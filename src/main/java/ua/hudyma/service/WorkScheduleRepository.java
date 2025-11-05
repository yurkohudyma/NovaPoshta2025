package ua.hudyma.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.WorkSchedule;

import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    Optional<WorkSchedule> findByDeliveryUnitId(Long unitId);
}
