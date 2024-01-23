package taplink.network.menu.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taplink.network.menu.api.models.Order;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "JOIN o.store s " +
            "JOIN s.users u " +
            "WHERE s.id = :storeId " +
            "AND u.username = :userName " +
            "AND (:statusId IS NULL OR o.orderStatusId = :statusId) " +
            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate)")
    Page<Order> searchOrders(Long storeId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable, String userName);
    
}
