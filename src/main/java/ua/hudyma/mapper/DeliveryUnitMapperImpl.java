package ua.hudyma.mapper;

import org.springframework.stereotype.Component;
import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.dto.DeliveryUnitRespDto;

import java.util.List;

@Component
public class DeliveryUnitMapperImpl implements DeliveryUnitMapper {
    @Override
    public DeliveryUnitRespDto toDto(DeliveryUnit unit) {
        return new DeliveryUnitRespDto(
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
    public List<DeliveryUnitRespDto> toDtoList(List<DeliveryUnit> list) {
        return list.stream().map(this::toDto).toList();
    }
}
