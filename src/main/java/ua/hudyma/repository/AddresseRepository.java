package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.Addressee;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AddresseRepository extends JpaRepository<Addressee, Long> {
    Optional<Addressee> findByAddresseeCode(String userCode);

    List<Addressee> findByDeliveryList_Sender_SenderCode(String senderCode);
}
