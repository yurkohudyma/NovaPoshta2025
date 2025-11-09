package ua.hudyma.mapper;

import ua.hudyma.domain.Delivery;
import ua.hudyma.dto.DeliveryRespDto;
import ua.hudyma.enums.DeliveryTrackDto;

public interface DeliveryMapper extends EntityMapper<DeliveryRespDto, Delivery> {
    DeliveryTrackDto toTrackDto (Delivery delivery);
}
