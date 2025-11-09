package ua.hudyma.dto;

import java.math.BigDecimal;

public record CurrentPositionDto(
        BigDecimal shipLat,
        BigDecimal shipLon,
        BigDecimal deliverLat,
        BigDecimal deliverLon) {
}
