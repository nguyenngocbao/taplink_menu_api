package taplink.network.menu.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllByCityId(Long cityId);
}
