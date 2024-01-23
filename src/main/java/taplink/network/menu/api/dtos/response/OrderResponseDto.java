package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long storeId;
    private Integer orderStatusId;
    private Set<OrderItemResponseDto> orderItemResponseDtos;
}
