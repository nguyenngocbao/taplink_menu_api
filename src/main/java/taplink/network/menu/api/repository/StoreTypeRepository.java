package taplink.network.menu.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.model.StoreType;

public interface StoreTypeRepository extends JpaRepository<StoreType, Long> {
}
