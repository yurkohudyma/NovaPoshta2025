package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ua.hudyma.enums.EntityType;
import ua.hudyma.util.IdGenerator;

@Entity
@Table(name = "addressees")
@Data
public class Addressee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String addresseeCode = IdGenerator.generateAddresseeCode();
    @Enumerated(EnumType.STRING)
    private EntityType addresseeType;
    @Embedded
    private Profile profile;
}
