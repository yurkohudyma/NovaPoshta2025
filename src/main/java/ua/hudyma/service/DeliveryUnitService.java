package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.City;
import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.domain.WorkSchedule;
import ua.hudyma.enums.DeliveryUnitReqDto;
import ua.hudyma.enums.DeliveryUnitsRespDto;
import ua.hudyma.enums.UnitType;
import ua.hudyma.exception.DtoObligatoryFieldsAreMissingException;
import ua.hudyma.mapper.DeliveryUnitMapper;
import ua.hudyma.mapper.DeliveryUnitMapperImpl;
import ua.hudyma.repository.DeliveryUnitRepository;

import java.util.List;
import java.util.Objects;

import static ua.hudyma.enums.UnitType.DEPARTMENT;
import static ua.hudyma.enums.UnitType.POSTOMATE;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeliveryUnitService {
    private final DeliveryUnitRepository deliveryUnitRepository;
    private final WorkScheduleRepository workScheduleRepository;
    private final CityService cityService;
    private final DeliveryUnitMapper deliveryUnitMapper;

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
        if (dto.dtoList() != null && !dto.dtoList().isEmpty()){
            schedule.setScheduleDtoList(dto.dtoList());
        }
        schedule.setDeliveryUnit(deliveryUnit);
        workScheduleRepository.save(schedule);
        deliveryUnit.setWorkSchedule(schedule);
        city.getDeliveryUnits().add(deliveryUnit);
    }

    private static void populateFields(DeliveryUnitReqDto dto,
                                       DeliveryUnit deliveryUnit,
                                       City city) {
        deliveryUnit.setDigitalAddress(city.getCityCode() + "/" + dto.unitNumber());
        UnitType unitType = dto.unitType();
        deliveryUnit.setUnitType(Objects.requireNonNullElse(unitType, DEPARTMENT));
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

    public DeliveryUnit findByDigitalAddress(String newDigitalAddress) {
        return deliveryUnitRepository.findByDigitalAddress(newDigitalAddress)
                .orElseThrow( () ->
                        new EntityNotFoundException("::: Delivery Unit "+ newDigitalAddress + " DOES not exist"));
    }

    public List<DeliveryUnitsRespDto> getAllDepatmentUnits() {
        return deliveryUnitMapper.toDtoList(deliveryUnitRepository.findAllByUnitType(DEPARTMENT));
    }

    public List<DeliveryUnitsRespDto> getAllPostomates() {
        return deliveryUnitMapper.toDtoList(
                deliveryUnitRepository.findAllByUnitType (POSTOMATE));
    }

    public DeliveryUnit getById(Long shippedFromId) {
        return deliveryUnitRepository.findById(shippedFromId).orElseThrow(
                () -> new EntityNotFoundException
                        ("Delivery Unit " + shippedFromId + " NOT FOUND"));
    }

    public List<String> getUsersDeliveryUnits(String userCode) {
        var userPrefix = userCode.split("\\.")[0];
        return switch (userPrefix) {
            case "AD" -> deliveryUnitRepository.findAllDeliveredToUnitsPerAddressee(userCode)
                    .stream()
                    .map(DeliveryUnit::getUnitAddress)
                    .distinct()
                    .toList();
            case "SE" -> deliveryUnitRepository.findAllShippedFromUnitsPerSender(userCode)
                    .stream()
                    .map(DeliveryUnit::getUnitAddress)
                    .distinct()
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
