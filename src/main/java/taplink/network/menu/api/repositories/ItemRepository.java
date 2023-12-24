package taplink.network.menu.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taplink.network.menu.api.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i " +
            "WHERE (i.name LIKE CONCAT('%', :searchKey, '%') OR i.description LIKE CONCAT('%', :searchKey, '%')) " +
            "AND i.active = true AND i.category.id = :categoryId ")
    Page<Item> searchItems(Long categoryId,
                           String searchKey,
                           Pageable pageable);

}
