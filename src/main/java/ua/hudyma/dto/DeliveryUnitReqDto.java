package ua.hudyma.dto;

import ua.hudyma.dto.ScheduleDto;
import ua.hudyma.enums.UnitType;

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
