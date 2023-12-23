package taplink.network.menu.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "CITIES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City extends BaseEntity {

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<District> districts;

}
