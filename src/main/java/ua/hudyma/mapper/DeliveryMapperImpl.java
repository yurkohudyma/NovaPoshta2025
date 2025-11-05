package ua.hudyma.mapper;

import org.springframework.stereotype.Component;
import ua.hudyma.domain.Delivery;
import ua.hudyma.enums.DeliveryRespDto;

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
}
