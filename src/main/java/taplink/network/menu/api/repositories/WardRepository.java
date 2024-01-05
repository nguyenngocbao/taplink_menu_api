package taplink.network.menu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.Ward;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findAllByDistrictId(Long districtId);
}
