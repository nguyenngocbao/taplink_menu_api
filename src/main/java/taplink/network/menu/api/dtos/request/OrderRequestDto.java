package taplink.network.menu.api.dtos.request;

import lombok.Data;

import java.util.Set;

@Data
public class OrderRequestDto {
    private Long storeId;
    private Set<OrderItemRequestDto> orderItemRequestDtos;
}
