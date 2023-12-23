package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.Ward;

public interface WardRepository extends JpaRepository<Ward, Long> {
}
