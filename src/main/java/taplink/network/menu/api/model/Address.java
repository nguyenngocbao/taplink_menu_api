package taplink.network.menu.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address extends BaseEntity {

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "WARD_ID")
    private Ward ward;

}
