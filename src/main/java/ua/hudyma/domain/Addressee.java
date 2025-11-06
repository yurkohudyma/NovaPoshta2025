package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ua.hudyma.enums.EntityType;
import ua.hudyma.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addressees")
@Data
public class Addressee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String addresseeCode;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    @Embedded
    private Profile profile;
    @OneToMany(mappedBy = "addressee")
    private List<Delivery> deliveryList = new ArrayList<>();
}
