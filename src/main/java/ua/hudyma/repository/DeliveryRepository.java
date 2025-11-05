package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.Delivery;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByAddressee_AddresseeCode(String userCode);

    List<Delivery> findAllBySender_SenderCode(String userCode);
}
