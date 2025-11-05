package ua.hudyma.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.WorkSchedule;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
}
