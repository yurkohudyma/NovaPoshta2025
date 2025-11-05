package ua.hudyma.dto;

public record DeliveryReqDto(
        Long shippedFromId,
        Long deliveredToId,
        Long senderId,
        Long addresseeId,
        Integer physicalweight,
        Integer height,
        Integer width,
        Integer length,
        String description
) {
}
