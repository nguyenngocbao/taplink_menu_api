package taplink.network.menu.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "ORDER")
    private Integer order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRICE_ID", nullable = false)
    private Price price;

}
