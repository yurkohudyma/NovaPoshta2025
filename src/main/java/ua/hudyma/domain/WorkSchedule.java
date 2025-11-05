package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "work_schedules")
@Data
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @MapsId
    private DeliveryUnit deliveryUnit;

    //todo expand with schedule hrs
}
