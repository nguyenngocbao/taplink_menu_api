package taplink.network.menu.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "WARDS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ward extends BaseEntity {

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private Set<Store> stores;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

}
