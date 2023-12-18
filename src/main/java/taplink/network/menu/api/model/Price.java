package taplink.network.menu.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "PRICE")
public class Price extends BaseEntity {

    @Column(name = "TYPE_ID", nullable = false)
    private Integer typeId;

    @Column(name = "VALUE", nullable = false)
    private String value;
}
