package taplink.network.menu.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "WARD")
public class Ward extends BaseEntity {

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @Column(name = "ORDER")
    private Integer order;

}
