package taplink.network.menu.api.commons.enums;

import lombok.Getter;

@Getter
public enum PriceType {

    SINGLE(1, "Single"),
    RANGE(2, "Range"),
    SIZE(3, "Size");

    private final int id;
    private final String name;

    PriceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
