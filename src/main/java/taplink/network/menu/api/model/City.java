package taplink.network.menu.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CITY")
public class City extends BaseEntity {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ORDER")
    private Integer order;

}
