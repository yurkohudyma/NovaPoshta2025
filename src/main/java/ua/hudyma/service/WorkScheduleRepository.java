package ua.hudyma.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.WorkSchedule;
import ua.hudyma.dto.ScheduleDto;

import java.util.List;
import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    Optional<WorkSchedule> findByDeliveryUnitId(Long unitId);

    Optional<WorkSchedule> findByDeliveryUnitId_DigitalAddress(String digitalAddress);
}
