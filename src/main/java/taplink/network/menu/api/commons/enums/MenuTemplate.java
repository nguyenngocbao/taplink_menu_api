package taplink.network.menu.api.commons.enums;

import lombok.Getter;

@Getter
public enum MenuTemplate {

    FOOD_AND_DRINK(1, "Food & Drink"),
    FOOD(2, "Food"),
    DRINK(3, "Drink"),
    SPA(4, "Spa"),
    NAIL(5, "Nail");

    private final int id;
    private final String name;

    MenuTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
