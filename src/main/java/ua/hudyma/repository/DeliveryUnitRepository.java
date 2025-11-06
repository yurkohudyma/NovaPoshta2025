package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.hudyma.domain.DeliveryUnit;

import java.util.List;
import java.util.Optional;

public interface DeliveryUnitRepository extends JpaRepository<DeliveryUnit, Long> {
    List<DeliveryUnit> findAllByFromDeliveryList_Sender_SenderCode(String userCode);
    List<DeliveryUnit> findAllByToDeliveryList_Addressee_AddresseeCode(String userCode);

    @Query(value = """
            select
            du.*
            FROM delivery_units du
            join deliveries d on d.shipped_from_id = du.id
            	join senders s on d.sender_id = s.id
            	    where s.sender_code = :userCode
                     """, nativeQuery = true)
    List<DeliveryUnit> findAllShippedFromUnitsPerSender(@Param("userCode")
                                                              String userCode);

    @Query(value = """
            select
            du.*
            FROM delivery_units du
            join deliveries d on d.delivered_to_id = du.id
            	join addressees a on d.addressee_id = a.id
            	    where a.addressee_code = :userCode
                     """, nativeQuery = true)
    List<DeliveryUnit> findAllDeliveredToUnitsPerAddressee(@Param("userCode")
                                                                String userCode);

    Optional<DeliveryUnit> findByDigitalAddress(String newDigitalAddress);
}
