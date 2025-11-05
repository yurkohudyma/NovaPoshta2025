package ua.hudyma.util;

import ua.hudyma.enums.DistanceDto;

import java.math.BigDecimal;

public class DistanceCalculator {
    private DistanceCalculator() {
    }

    private static final double EARTH_RADIUS_KM = 6371;

    public static double haversine(DistanceDto dto) {
        var lat1 = dto.shipLat().doubleValue();
        var lon1 = dto.shipLon().doubleValue();
        var lat2 = dto.deliverLat().doubleValue();
        var lon2 = dto.deliverLon().doubleValue();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
