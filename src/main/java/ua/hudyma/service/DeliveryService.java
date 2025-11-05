package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.Delivery;
import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.dto.DeliveryReqDto;
import ua.hudyma.exception.DtoObligatoryFieldsAreMissingException;
import ua.hudyma.repository.DeliveryRepository;
import ua.hudyma.repository.DeliveryUnitRepository;
import ua.hudyma.util.IdGenerator;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryUnitService deliveryUnitService;
    private final SenderService senderService;
    private final AddresseeService addresseeService;

    public void createDelivery(DeliveryReqDto dto) {
        if (dto == null || checkObligatoryFields(dto)){
            throw new DtoObligatoryFieldsAreMissingException("DeliveryReqDto cannot be NULL");
        }
        var delivery = new Delivery();
        delivery.setShippedFrom(deliveryUnitService.getById(dto.shippedFromId()));
        delivery.setDeliveredTo(deliveryUnitService.getById(dto.deliveredToId()));
        delivery.setSender(senderService.getById (dto.senderId()));
        delivery.setAddressee(addresseeService.getById (dto.addresseeId()));
        delivery.setEstimatedDelivery(delivery.getCreatedOn().plusDays(2));
        delivery.setDeliveryCost(BigDecimal.valueOf(IdGenerator.generateRandomDigits()));
        delivery.setWeight(calculateWeightAndAcceptBigger (dto));
        deliveryRepository.save(delivery);
        log.info("::: delivery {} CREATED", delivery.getTtn());
    }

    private Integer calculateWeightAndAcceptBigger(DeliveryReqDto dto) {
        var volumeWeight = dto.height() * dto.height() * dto.length() / 4000;
        Integer physicalweight = dto.physicalweight();
        log.info("Volume weight = {}, physical = {}" , volumeWeight, physicalweight);
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
//todo вимірювання ваги — фактичної та обʼємної.
// Яка із них більша — ту й враховуємо.
// Фактична вага - це звичайна вага.
// Обʼємна вага - визначається габаритами відправлення.
// висота х ширина х довжина / 4000 = обʼємна вага.