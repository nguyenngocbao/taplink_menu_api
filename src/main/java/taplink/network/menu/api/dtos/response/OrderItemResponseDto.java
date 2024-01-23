package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    Long itemId;
    Integer quantities;
    String priceInfo;
    Long orderId;
    private Long id;
}
