package taplink.network.menu.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Long> {
}
