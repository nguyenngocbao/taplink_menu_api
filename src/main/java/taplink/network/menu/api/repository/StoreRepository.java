package taplink.network.menu.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taplink.network.menu.api.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT s FROM Store s WHERE " +
            "(s.name LIKE CONCAT('%', :searchKey, '%')) OR " +
            "(s.phone LIKE CONCAT('%', :searchKey, '%') OR " +
            "s.email LIKE CONCAT('%', :searchKey, '%')) AND " +
            "s.active = true")
    Page<Store> searchStores(String searchKey, Pageable pageable);

}
