package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.DeliveryUnit;

public interface DeliveryUnitRepository extends JpaRepository<DeliveryUnit, Long> {
}
