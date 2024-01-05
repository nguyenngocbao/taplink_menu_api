package taplink.network.menu.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import taplink.network.menu.api.models.Category;
import taplink.network.menu.api.models.Device;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByUuid(String uuid);
}
