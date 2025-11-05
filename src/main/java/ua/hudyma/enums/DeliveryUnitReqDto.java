package ua.hudyma.enums;

import java.math.BigDecimal;

public record DeliveryUnitReqDto(
        Integer unitNumber,
        String unitAddress,
        Long cityId,
        Integer maxWeightAccepted,
        Integer maxLength,
        Integer maxWidth,
        Integer maxHeight,
        BigDecimal latitude,
        BigDecimal longitude) {
}
