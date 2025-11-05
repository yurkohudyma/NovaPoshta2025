package ua.hudyma.enums;

import java.math.BigDecimal;

public record DeliveryRespDto
        (
                String ttn,
                Integer shippedFromNumber,
                String shippedFromAddress,
                Integer deliveredToNumber,
                String deliveredToAddress,
                String senderName,
                String addresseeName,
                DeliveryStatus deliveryStatus,
                BigDecimal declaredValue,
                BigDecimal deliveryCost,
                DeliveryCostPayer deliveryCostPayer,
                Integer weight,
                Integer items,
                String description

        ) {
}
