package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.StoreType;

public interface StoreTypeRepository extends JpaRepository<StoreType, Long> {
}
