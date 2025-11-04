package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import ua.hudyma.enums.EntityType;
import ua.hudyma.util.IdGenerator;

@Entity
@Table(name = "senders")
@Data
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(unique = true)
    private String senderCode = IdGenerator.generateSenderCode();
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    @Embedded
    private Profile profile;
}
