package taplink.network.menu.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
