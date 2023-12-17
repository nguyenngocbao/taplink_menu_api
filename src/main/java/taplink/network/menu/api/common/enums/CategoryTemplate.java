package taplink.network.menu.api.common.enums;

import lombok.Getter;

@Getter
public enum CategoryTemplate {
    IMAGES(1, "Image"),
    NO_IMAGES(2, "No image");

    private final int id;
    private final String name;

    CategoryTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
