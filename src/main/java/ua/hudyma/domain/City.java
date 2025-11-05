package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import ua.hudyma.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private Integer cityCode = IdGenerator.generateCityCode();
    private String cityName;
    @OneToMany(mappedBy = "city")
    @Setter(AccessLevel.PRIVATE)
    private List<DeliveryUnit> deliveryUnits = new ArrayList<>();
}
