package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(String code);

}
