package ua.hudyma.enums;

import ua.hudyma.dto.ScheduleDto;

import java.math.BigDecimal;
import java.util.List;

public record DeliveryUnitReqDto(
        Integer unitNumber,
        UnitType unitType,
        String unitAddress,
        String digitalAddress,
        Long cityId,
        Integer maxWeightAccepted,
        Integer maxLength,
        Integer maxWidth,
        Integer maxHeight,
        BigDecimal latitude,
        BigDecimal longitude,
        List<ScheduleDto> dtoList) {
}
