package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
