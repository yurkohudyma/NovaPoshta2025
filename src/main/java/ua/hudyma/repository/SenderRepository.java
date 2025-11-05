package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.Sender;

import java.util.Optional;

public interface SenderRepository extends JpaRepository<Sender, Long> {
    Optional<Sender> findBySenderCode(String userCode);
}
