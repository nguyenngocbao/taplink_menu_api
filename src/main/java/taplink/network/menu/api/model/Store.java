package taplink.network.menu.api.model;

import jakarta.persistence.*;
import taplink.network.menu.api.common.enums.StoreTemplate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STORE")
public class Store extends BaseEntity {

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "WIFI_PASSWORD")
    private String wifiPass;

    @Column(name = "TEMPLATE_ID")
    private Integer templateId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private Set<Device> devices = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "STORE_TYPE_ID")
    private StoreType storeType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MENU_ID", nullable = false)
    private Menu menu;

}
