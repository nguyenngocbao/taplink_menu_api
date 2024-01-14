package taplink.network.menu.api.commons.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.OrderRequestDto;
import taplink.network.menu.api.dtos.response.OrderItemResponseDto;
import taplink.network.menu.api.dtos.response.OrderResponseDto;
import taplink.network.menu.api.models.Order;
import taplink.network.menu.api.models.OrderItem;
import taplink.network.menu.api.models.Store;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final ObjectMapperUtils objectMapperUtils;
    private final OrderItemConverter orderItemConverter;

    public Order convertToNewEntityFromDto(OrderRequestDto dto, Store store) {
        Order order = objectMapperUtils.convertEntityAndDto(dto, Order.class);
        order.setStore(store);
        return order;
    }

    public Order convertToPersistedEntityFromDto(Order order, OrderRequestDto dto, Store store, Set<OrderItem> orderItems) {
        objectMapperUtils.convertToPersistedEntityFromDto(order, dto);
        order.setStore(store);
        order.setOrderItems(orderItems);
        return order;
    }

    public List<OrderResponseDto> convertToDtoFromEntity(List<Order> orders) {
        return orders.stream().map(order -> {
            OrderResponseDto orderResponseDto = objectMapperUtils.convertEntityAndDto(order, OrderResponseDto.class);
            orderResponseDto.setStoreId(order.getStore().getId());
            Set<OrderItemResponseDto> orderItemResponseDtos = orderItemConverter.convertToDtoFromEntity(order.getOrderItems());
            orderResponseDto.setOrderItemResponseDtos(orderItemResponseDtos);
            return orderResponseDto;
        }).collect(Collectors.toList());
    }

}
