package taplink.network.menu.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STORES")
@Getter
@Setter
public class Store extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "WIFI_PASSWORD")
    private String wifiPass;

    @Column(name = "STORE_TEMPLATE_ID")
    private Integer storeTemplateId;

    @Column(name = "MENU_TEMPLATE_ID")
    private Integer menuTemplateId;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WARD_ID")
    private Ward ward;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Device> devices = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_TYPE_ID")
    private StoreType storeType;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserStoreRole> userStoreRoles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

}
