package taplink.network.menu.api.model;

import jakarta.persistence.*;
import taplink.network.menu.api.common.enums.MenuTemplate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MENU")
public class Menu extends BaseEntity {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MENU_ID")
    private Set<Category> categories = new HashSet<>();

    @Column(name = "TEMPLATE_ID")
    private Integer templateId;

}
