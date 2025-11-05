package ua.hudyma.mapper;

import ua.hudyma.domain.Delivery;
import ua.hudyma.enums.DeliveryRespDto;

import java.util.List;

public interface DeliveryMapper {

    DeliveryRespDto toDto (Delivery delivery);
    List<DeliveryRespDto> toDtoList (List<Delivery> deliveryList);

}
