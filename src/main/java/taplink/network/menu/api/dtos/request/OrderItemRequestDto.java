package taplink.network.menu.api.dtos.request;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long itemId;
    private Integer quantities;
}
