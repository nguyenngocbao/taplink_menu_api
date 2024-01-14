package taplink.network.menu.api.commons.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED(1, "Created"),
    PROCESSING(2, "Processing"),
    DELIVERED(3, "Delivered"),
    PAID(4, "Paid"),
    COMPLETED(5, "Completed"),
    CANCELLED(6, "Cancelled"),
    ON_HOLD(7, "On Hold"),
    REFUNDED(8, "Refunded");

    private final int id;
    private final String name;

    OrderStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
