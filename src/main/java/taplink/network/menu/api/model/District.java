package taplink.network.menu.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DISTRICT")
public class District extends BaseEntity {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ORDER")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

}
