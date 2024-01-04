package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.UserStoreRole;

public interface UserStoreRoleRepository extends JpaRepository<UserStoreRole, Long> {
}
