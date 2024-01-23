package taplink.network.menu.api.commons.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.OrderItemRequestDto;
import taplink.network.menu.api.dtos.response.OrderItemResponseDto;
import taplink.network.menu.api.models.Item;
import taplink.network.menu.api.models.Order;
import taplink.network.menu.api.models.OrderItem;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    private final ObjectMapperUtils objectMapperUtils;

    public OrderItem convertToNewEntityFromDto(OrderItemRequestDto dto, Item item) {
        OrderItem orderItem = objectMapperUtils.convertEntityAndDto(dto, OrderItem.class);
        orderItem.setItem(item);
        orderItem.setPriceInfo(item.getPriceInfo());
        return orderItem;
    }

    public OrderItem convertToPersistedEntityFromDto(OrderItem orderItem, OrderItemRequestDto dto, Item item, Order order) {
        objectMapperUtils.convertToPersistedEntityFromDto(orderItem, dto);
        orderItem.setOrder(order);
        orderItem.setItem(item);
        return orderItem;
    }

    public Set<OrderItemResponseDto> convertToDtoFromEntity(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> {
                    OrderItemResponseDto orderItemResponseDto = objectMapperUtils.convertEntityAndDto(orderItem, OrderItemResponseDto.class);
                    orderItemResponseDto.setItemId(orderItem.getItem().getId());
                    orderItemResponseDto.setOrderId(orderItem.getItem().getId());
                    return orderItemResponseDto;
                })
                .collect(Collectors.toSet());
    }

}
