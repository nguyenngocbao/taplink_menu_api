package taplink.network.menu.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
