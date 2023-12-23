package taplink.network.menu.api.commons.enums;

import lombok.Getter;

@Getter
public enum StoreTemplate {

    RESTAURANT(1, "Restaurant"),
    CAFE(2, "Cafe"),
    SPA(3, "Spa"),
    NAIL(4, "Nail");

    private final int id;
    private final String name;

    StoreTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
