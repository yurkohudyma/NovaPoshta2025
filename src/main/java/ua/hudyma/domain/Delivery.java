package ua.hudyma.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import ua.hudyma.dto.CurrentPositionDto;
import ua.hudyma.enums.DeliveryCostPayer;
import ua.hudyma.enums.DeliveryStatus;
import ua.hudyma.util.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String ttn = IdGenerator.generateTtn();
    @ManyToOne
    @JoinColumn(name = "shipped_from_id")
    private DeliveryUnit shippedFrom;
    @ManyToOne
    @JoinColumn(name = "delivered_to_id")
    private DeliveryUnit deliveredTo;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender sender;
    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private Addressee addressee;
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus = DeliveryStatus.CREATED;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime sentOn;
    private LocalDateTime deliveredOn;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime payableStorageSince;
    private BigDecimal declaredValue = BigDecimal.valueOf(200);
    private BigDecimal deliveryCost;
    private Integer weight;
    private Integer items = 1;
    private String description;
    private String measurements;
    @Enumerated(value = EnumType.STRING)
    private DeliveryCostPayer deliveryCostPayer = DeliveryCostPayer.SENDER;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", name = "currente_pos_dto")
    private CurrentPositionDto currentPositionDto;

}
