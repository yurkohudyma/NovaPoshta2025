package ua.hudyma.enums;

import ua.hudyma.dto.CurrentPositionDto;

import java.time.LocalDateTime;

public record DeliveryTrackDto(
        CurrentPositionDto positionDto,
        String shippedFrom,
        String description,
        Integer weight,
        String measurementWeight,
        DeliveryCostPayer costPayer,
        LocalDateTime estimatedDelivery
) {
}
