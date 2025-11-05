package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ua.hudyma.enums.EntityType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "senders")
@Data
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String senderCode;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    @Embedded
    private Profile profile;
    @OneToMany(mappedBy = "sender")
    private List<Delivery> deliveryList = new ArrayList<>();
}
