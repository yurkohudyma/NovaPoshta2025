package ua.hudyma.enums;

import ua.hudyma.domain.City;
import ua.hudyma.domain.WorkSchedule;

import java.math.BigDecimal;

public record DeliveryUnitReqDto(
        Integer unitNumber,
        String unitAddress,
        Long cityId,
        Integer maxWeightAccepted,
        Integer maxLength, //120x70x70 cm
        Integer maxWidth,
        Integer maxHeight,
        Integer workScheduleId,
        BigDecimal latitude,
        BigDecimal longitude) {
}
