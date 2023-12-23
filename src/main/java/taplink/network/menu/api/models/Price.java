package taplink.network.menu.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "PRICES")
@Getter
@Setter
public class Price extends BaseEntity {

    @Column(name = "TYPE_ID", nullable = false)
    private Integer typeId;

    @Column(name = "VALUE", nullable = false)
    private String value;
}
