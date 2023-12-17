package taplink.network.menu.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DEVICE")
public class Device extends BaseEntity {

    @Column(name = "UUID", nullable = false, unique = true)
    private String uuid;

    @Column(name = "ACTIVE")
    private boolean active;

}
