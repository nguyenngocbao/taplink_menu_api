package taplink.network.menu.api.commons.enums;

import lombok.Getter;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;

@Getter
public enum OrderStatus {
    CREATED(1, "Created", new int[]{2, 7}),
    PROCESSING(2, "Processing", new int[]{3, 7, 8}),
    DELIVERED(3, "Delivered", new int[]{4, 8}),
    PAYMENT_PENDING(4, "Payment Pending", new int[]{5}),
    PAID(5, "Paid", new int[]{6, 9}),
    COMPLETED(6, "Completed", new int[]{}),
    CANCELLED(7, "Cancelled", new int[]{}),
    ON_HOLD(8, "On Hold", new int[]{2, 3}),
    REFUNDED(9, "Refunded", new int[]{});

    private final int id;
    private final String name;
    private final int[] nextStatuses;

    OrderStatus(int id, String name, int[] nextStatuses) {
        this.id = id;
        this.name = name;
        this.nextStatuses = nextStatuses;
    }

    public static String getOrderStatusNameById(int id) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getId() == id) {
                return orderStatus.getName();
            }
        }
        throw new ResourceNotFoundException("Order Status", Long.valueOf(id));
    }

    public static int[] getNextStatusesById(int id) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getId() == id) {
                return orderStatus.getNextStatuses();
            }
        }
        throw new ResourceNotFoundException("Order Status", Long.valueOf(id));
    }
}
