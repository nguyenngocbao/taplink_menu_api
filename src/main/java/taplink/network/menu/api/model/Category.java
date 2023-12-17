package taplink.network.menu.api.model;

import jakarta.persistence.*;
import taplink.network.menu.api.common.enums.CategoryTemplate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "TEMPLATE_ID")
    private Integer templateId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Set<Item> items = new HashSet<>();

}
