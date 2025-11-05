package ua.hudyma.enums;

import java.math.BigDecimal;

public record DistanceDto(
        BigDecimal shipLat,
        BigDecimal shipLon,
        BigDecimal deliverLat,
        BigDecimal deliverLon) {
}
