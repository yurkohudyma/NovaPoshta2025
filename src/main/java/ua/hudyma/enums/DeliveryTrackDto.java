package ua.hudyma.enums;

import ua.hudyma.dto.CurrentPositionDto;

public record DeliveryTrackDto(
        CurrentPositionDto positionDto,
        String shippedFrom,
        String description,
        Integer weight,
        String measurementWeight,
        DeliveryCostPayer costPayer,
        String estimatedDelivery
) {
}
