package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.Sender;

public interface SenderRepository extends JpaRepository<Sender, Long> {
}
