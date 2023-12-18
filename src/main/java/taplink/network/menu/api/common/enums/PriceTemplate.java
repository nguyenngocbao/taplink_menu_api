package taplink.network.menu.api.common.enums;

import lombok.Getter;

@Getter
public enum PriceTemplate {

    SINGLE(1, "Single"),
    RANGE(2, "Range"),
    SIZE(3, "Size");

    private final int id;
    private final String name;

    PriceTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
