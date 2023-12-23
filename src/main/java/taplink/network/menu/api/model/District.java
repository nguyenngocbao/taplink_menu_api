package taplink.network.menu.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "DISTRICTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class District extends BaseEntity {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "SORT_ORDER")
    private Integer sort_order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private City city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private Set<Ward> wards;

}
