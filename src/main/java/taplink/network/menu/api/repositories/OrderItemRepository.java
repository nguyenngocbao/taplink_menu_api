package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
