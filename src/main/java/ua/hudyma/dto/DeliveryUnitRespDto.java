package ua.hudyma.dto;

import java.util.List;

public record DeliveryUnitRespDto(
        Integer unitNumber,
        String unitAddress,
        String digitalAddress,
        String cityName,
        Integer maxWeightAccepted,
        String maxMeasurements,
        List<ScheduleDto> workScheduleDto,
        String geoLocation
) {
}
