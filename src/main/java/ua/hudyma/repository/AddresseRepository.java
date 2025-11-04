package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.Addressee;

public interface AddresseRepository extends JpaRepository<Addressee, Long> {
}
