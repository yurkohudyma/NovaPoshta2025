package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.Delivery;
import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.dto.DeliveryReqDto;
import ua.hudyma.enums.DeliveryCostPayer;
import ua.hudyma.enums.DeliveryRespDto;
import ua.hudyma.enums.DeliveryStatus;
import ua.hudyma.enums.DistanceDto;
import ua.hudyma.exception.DeliveryTolerancesExcessException;
import ua.hudyma.exception.DtoObligatoryFieldsAreMissingException;
import ua.hudyma.mapper.DeliveryMapper;
import ua.hudyma.repository.AddresseRepository;
import ua.hudyma.repository.DeliveryRepository;
import ua.hudyma.repository.SenderRepository;
import ua.hudyma.util.DistanceCalculator;
import ua.hudyma.util.IdGenerator;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryUnitService deliveryUnitService;
    private final SenderService senderService;
    private final AddresseeService addresseeService;
    private final SenderRepository senderRepository;
    private final AddresseRepository addresseeRepository;
    private final DeliveryMapper deliveryMapper;

    @Transactional
    public void createDelivery(DeliveryReqDto dto) {
        if (dto == null || checkObligatoryFields(dto)){
            throw new DtoObligatoryFieldsAreMissingException("DeliveryReqDto cannot be NULL " +
                    "or missing obigatory dto fields");
        }
        var shippedFromUnit = deliveryUnitService.getById(dto.shippedFromId());
        checkMaxWeightAndMeasurement(dto, shippedFromUnit);
        var delivery = new Delivery();
        delivery.setShippedFrom(shippedFromUnit);
        var deliveredToUnit = deliveryUnitService.getById(dto.deliveredToId());
        delivery.setDeliveredTo(deliveredToUnit);
        delivery.setSender(senderService.getById (dto.senderId()));
        delivery.setAddressee(addresseeService.getById (dto.addresseeId()));
        delivery.setDeliveryCost(BigDecimal.valueOf(IdGenerator.generateRandomDigits()));
        delivery.setWeight(calculateWeightAndAcceptBigger (dto));
        delivery.setDescription("Продукти");
        delivery.setDeliveryCost(calculateDeliveryCost(delivery.getWeight(), shippedFromUnit, deliveredToUnit));
        deliveryRepository.save(delivery);
        delivery.setEstimatedDelivery(delivery.getCreatedOn().plusDays(2));
        log.info("::: delivery {} CREATED", delivery.getTtn());
    }

    private static BigDecimal calculateDeliveryCost(Integer weight,
                                             DeliveryUnit shippedFromUnit,
                                             DeliveryUnit deliveredToUnit) {

        return BigDecimal.valueOf(DistanceCalculator.haversine(
                new DistanceDto(
                shippedFromUnit.getLatitude(),
                shippedFromUnit.getLatitude(),
                deliveredToUnit.getLatitude(),
                deliveredToUnit.getLongitude())) * weight / 100);
    }

    private static void checkMaxWeightAndMeasurement(DeliveryReqDto dto, DeliveryUnit shippedFromUnit) {
        if(dto.physicalweight() > shippedFromUnit.getMaxWeightAccepted()){
            throw new DeliveryTolerancesExcessException(
                    "Max weight per unit = " + shippedFromUnit.getMaxWeightAccepted() +
                            ", while required " + dto.physicalweight());
        }
        else if (dto.length() > shippedFromUnit.getMaxLength() ||
                dto.width() > shippedFromUnit.getMaxWidth() ||
                dto.height() > shippedFromUnit.getMaxHeight()){
            throw new DeliveryTolerancesExcessException(
                    "Delivery measurements could not be accepted due to unit restrictions: " +
                            compileDeliveryMeasurementVsUnitTolerances (dto, shippedFromUnit));
        }
    }

    private static String compileDeliveryMeasurementVsUnitTolerances(DeliveryReqDto dto,
                                                                     DeliveryUnit shippedFromUnit) {
        return String.format("Delivery request (l/w/h): %d/%d/%d, unit requirement: %d/%d/%d",
                dto.length(), dto.width(), dto.height(),
                shippedFromUnit.getMaxLength(),
                shippedFromUnit.getMaxWidth(),
                shippedFromUnit.getMaxHeight());
    }

    public List<DeliveryRespDto> getAllDeliveriesByUserCode(String userCode) {
        var sender = senderRepository.findBySenderCode (userCode);
        if (sender.isEmpty()){
            var addressee = addresseeRepository.findByAddresseeCode (userCode);
            if (addressee.isEmpty()){
                throw new EntityNotFoundException(
                        ":::: Neither SENDER nor ADDRESSEE has been recognised by userCode " + userCode);
            }
            var deliveryList = deliveryRepository.findAllByAddressee_AddresseeCode(userCode);
            return deliveryMapper.toDtoList (deliveryList);
        }
        var deliveryList = deliveryRepository.findAllBySender_SenderCode(userCode);
        return deliveryMapper.toDtoList(deliveryList);
    }

    @Transactional
    public void redirectDelivery(String ttn, String newDigitalAddress) {
        var delivery = deliveryRepository.findByTtn(ttn).orElseThrow(
                () -> new EntityNotFoundException(":::: Delivery " + ttn + " DOES NOT exist"));
        var newDeliveryUnit = deliveryUnitService.findByDigitalAddress(newDigitalAddress);
        delivery.setDeliveredTo(newDeliveryUnit);
        delivery.setDeliveryStatus(DeliveryStatus.REDIRECTED);
    }

    @Transactional
    public void refuseDelivery(String ttn) {
        var delivery = deliveryRepository.findByTtn(ttn).orElseThrow(
                () -> new EntityNotFoundException(":::: Delivery " + ttn + " DOES NOT exist"));
        var deliveryToUnit = delivery.getDeliveredTo();
        var deliveryFromUnit = delivery.getShippedFrom();
        delivery.setDeliveredTo(deliveryFromUnit);
        delivery.setShippedFrom(deliveryToUnit);
        delivery.setDeliveryStatus(DeliveryStatus.REFUSED);
        delivery.setDeliveryCostPayer(DeliveryCostPayer.NOVAPOSHTA);
    }

    private Integer calculateWeightAndAcceptBigger(DeliveryReqDto dto) {
        var volumeWeight = dto.height() * dto.height() * dto.length() / 4000;
        Integer physicalweight = dto.physicalweight();
        log.info(":::: Volume weight = {}, physical = {}" , volumeWeight, physicalweight);
        return volumeWeight > physicalweight ? volumeWeight : physicalweight;
    }

    private boolean checkObligatoryFields(DeliveryReqDto dto) {
        return  dto.shippedFromId() == null ||
                dto.addresseeId() == null ||
                dto.deliveredToId() == null ||
                dto.senderId() == null ||
                dto.physicalweight() == null ||
                dto.length() == null ||
                dto.height() == null ||
                dto.width() == null;
    }
}