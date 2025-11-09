package ua.hudyma.enums;

import ua.hudyma.dto.ScheduleDto;

import java.util.List;

public record DeliveryUnitsRespDto(
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
