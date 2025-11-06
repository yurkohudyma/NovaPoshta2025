package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.*;
import ua.hudyma.enums.DeliveryUnitReqDto;
import ua.hudyma.exception.DtoObligatoryFieldsAreMissingException;
import ua.hudyma.repository.AddresseRepository;
import ua.hudyma.repository.DeliveryUnitRepository;
import ua.hudyma.repository.SenderRepository;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeliveryUnitService {
    private final DeliveryUnitRepository deliveryUnitRepository;
    private final WorkScheduleRepository workScheduleRepository;
    private final AddresseRepository addresseRepository;
    private final CityService cityService;
    private final SenderRepository senderRepository;

    @Transactional
    public void createDeliveryUnit(DeliveryUnitReqDto dto) {
        if (dto == null || checkObligatoryFields(dto)) {
            throw new DtoObligatoryFieldsAreMissingException("DeliveryReqDto cannot be NULL " +
                    "or missing obigatory dto fields");
        }
        var deliveryUnit = new DeliveryUnit();
        var city = cityService.getCityByID(dto.cityId());
        populateFields(dto, deliveryUnit, city);
        deliveryUnitRepository.save(deliveryUnit);
        log.info(":::: Delivery Unit {} CREATED", deliveryUnit.getUnitAddress());
        var schedule = new WorkSchedule();
        schedule.setDeliveryUnit(deliveryUnit);
        workScheduleRepository.save(schedule);
        deliveryUnit.setWorkSchedule(schedule);
        city.getDeliveryUnits().add(deliveryUnit);
    }

    private static void populateFields(DeliveryUnitReqDto dto,
                                       DeliveryUnit deliveryUnit,
                                       City city) {
        deliveryUnit.setDigitalAddress(city.getCityCode() + "/" + dto.unitNumber());
        deliveryUnit.setUnitNumber(dto.unitNumber());
        deliveryUnit.setUnitAddress(dto.unitAddress());
        deliveryUnit.setCity(city);
        deliveryUnit.setUnitAddress(dto.unitAddress());
        deliveryUnit.setLatitude(dto.latitude());
        deliveryUnit.setLongitude(dto.longitude());
        deliveryUnit.setMaxHeight(dto.maxHeight());
        deliveryUnit.setMaxWidth(dto.maxWidth());
        deliveryUnit.setMaxLength(dto.maxLength());
        deliveryUnit.setMaxWeightAccepted(dto.maxWeightAccepted());
    }

    public DeliveryUnit getById(Long shippedFromId) {
        return deliveryUnitRepository.findById(shippedFromId).orElseThrow(
                () -> new EntityNotFoundException
                        ("Delivery Unit " + shippedFromId + " NOT FOUND"));
    }

    public List<String> getUsersDeliveryUnits(String userCode) {
        Supplier<Exception> exception = EntityNotFoundException::new;
        var userPrefix = userCode.split("\\.")[0];
        return switch (userPrefix) {
            case "AD" -> deliveryUnitRepository.findAllDeliveredToUnitsPerAddressee(userCode)
                    .stream()
                    .map(DeliveryUnit::getUnitAddress)
                    .toList();
            case "SE" -> deliveryUnitRepository.findAllShippedFromUnitsPerSender(userCode)
                    .stream()
                    .map(DeliveryUnit::getUnitAddress)
                    .toList();
            default -> throw new IllegalArgumentException("CASE scenario UNKNOWN");
        };
    }

    private boolean checkObligatoryFields(DeliveryUnitReqDto dto) {
        return dto.unitNumber() == null ||
                dto.unitAddress() == null ||
                dto.cityId() == null ||
                dto.maxWeightAccepted() == null ||
                dto.maxLength() == null ||
                dto.maxWidth() == null ||
                dto.maxHeight() == null ||
                dto.latitude() == null ||
                dto.longitude() == null;
    }
}
