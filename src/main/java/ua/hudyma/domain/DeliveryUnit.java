package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_units")
@Data
public class DeliveryUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String digitalAddress; //todo cityNum + proprietory
    private Integer unitNumber;
    private String unitAddress;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "shippedFrom")
    @Setter(AccessLevel.PRIVATE)
    private List<Delivery> fromDeliveryList = new ArrayList<>();
    @OneToMany(mappedBy = "deliveredTo")
    @Setter(AccessLevel.PRIVATE)
    private List<Delivery> toDeliveryList = new ArrayList<>();
    private Integer maxWeightAccepted;
    private Integer maxLength;
    private Integer maxWidth;
    private Integer maxHeight;
    @OneToOne(mappedBy = "deliveryUnit")
    private WorkSchedule workSchedule;
    @Column(precision = 38, scale = 15)
    private BigDecimal latitude;
    @Column(precision = 38, scale = 15)
    private BigDecimal longitude;
}
