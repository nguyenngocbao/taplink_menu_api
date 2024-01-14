package taplink.network.menu.api.dtos.request;

import lombok.Data;
import taplink.network.menu.api.models.Item;

import java.util.Set;

@Data
public class OrderRequestDto {
    private Long storeId;
    private Integer orderStatusId;
    private Set<OrderItemRequestDto> orderItemRequestDtos;
}
