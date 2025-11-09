package ua.hudyma.mapper;

import ua.hudyma.domain.DeliveryUnit;
import ua.hudyma.enums.DeliveryUnitsRespDto;

import java.util.List;

public interface DeliveryUnitMapper {
    DeliveryUnitsRespDto toDto (DeliveryUnit unit);
    List<DeliveryUnitsRespDto> toDtoList (List<DeliveryUnit> list);
}
