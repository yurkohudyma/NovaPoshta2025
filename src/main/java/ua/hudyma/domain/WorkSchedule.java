package ua.hudyma.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ua.hudyma.dto.ScheduleDto;

import java.util.ArrayList;
import java.util.List;

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
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", name = "schedule_dto_list")
    List<ScheduleDto> scheduleDtoList = new ArrayList<>();
}
