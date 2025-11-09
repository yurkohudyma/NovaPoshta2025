package ua.hudyma.mapper;

import org.springframework.stereotype.Component;
import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.enums.DeliveryUnitsRespDto;

import java.util.List;

@Component
public class DeliveryUnitMapperImpl implements DeliveryUnitMapper {
    @Override
    public DeliveryUnitsRespDto toDto(DeliveryUnit unit) {
        return new DeliveryUnitsRespDto(
                unit.getUnitNumber(),
                unit.getUnitAddress(),
                unit.getDigitalAddress(),
                unit.getCity().getCityName(),
                unit.getMaxWeightAccepted(),
                getMaxMeasurements(unit),
                unit.getWorkSchedule().getScheduleDtoList(),
                unit.getLatitude() + ", " + unit.getLongitude()
        );
    }

    private String getMaxMeasurements(DeliveryUnit unit) {
        return String.format("%d/%d/%d", unit.getMaxLength(), unit.getMaxWidth(), unit.getMaxHeight());
    }

    @Override
    public List<DeliveryUnitsRespDto> toDtoList(List<DeliveryUnit> list) {
        return list.stream().map(this::toDto).toList();
    }
}
