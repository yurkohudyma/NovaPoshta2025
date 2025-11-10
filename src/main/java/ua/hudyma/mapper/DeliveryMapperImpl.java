package ua.hudyma.mapper;

import org.springframework.stereotype.Component;
import ua.hudyma.domain.Delivery;
import ua.hudyma.dto.DeliveryRespDto;
import ua.hudyma.enums.DeliveryTrackDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DeliveryMapperImpl implements DeliveryMapper {
    @Override
    public DeliveryRespDto toDto(Delivery delivery) {
        return new DeliveryRespDto(
                delivery.getTtn(),
                delivery.getShippedFrom().getUnitNumber(),
                delivery.getShippedFrom().getUnitAddress(),
                delivery.getDeliveredTo().getUnitNumber(),
                delivery.getDeliveredTo().getUnitAddress(),
                delivery.getSender().getProfile().getName(),
                delivery.getAddressee().getProfile().getName(),
                delivery.getDeliveryStatus(),
                delivery.getDeclaredValue(),
                delivery.getDeliveryCost(),
                delivery.getDeliveryCostPayer(),
                delivery.getWeight(),
                delivery.getItems(),
                delivery.getDescription()
        );
    }

    @Override
    public List<DeliveryRespDto> toDtoList(List<Delivery> deliveryList) {
        return deliveryList.stream().map(this::toDto).toList();
    }

    @Override
    public DeliveryTrackDto toTrackDto(Delivery delivery) {
        return new DeliveryTrackDto(
                delivery.getCurrentPositionDto(),
                delivery.getShippedFrom().getUnitNumber() + "/" + delivery.getShippedFrom().getUnitAddress(),
                delivery.getDescription(),
                delivery.getWeight(),
                delivery.getMeasurements(),
                delivery.getDeliveryCostPayer(),
                normaliseDate(delivery.getEstimatedDelivery())
        );
    }

    private String normaliseDate(LocalDateTime estimatedDelivery) {
        return String.format("%d-%s-%d %d:%d",
                estimatedDelivery.getDayOfMonth(),
                estimatedDelivery.getMonth().getValue(),
                estimatedDelivery.getYear(),
                estimatedDelivery.getHour(),
                estimatedDelivery.getMinute());
    }
}
