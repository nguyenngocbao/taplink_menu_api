package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.OrderRequestDto;
import taplink.network.menu.api.dtos.response.OrderResponseDto;
import taplink.network.menu.api.dtos.response.OrderStatusDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    ResponseDto<OrderResponseDto> searchOrders(Long storeId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate, int pageNo, int pageSize, String sortBy, String sortDir);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto findById(Long id);

    OrderResponseDto updateOrderStatus(Long id, OrderRequestDto orderRequestDto);

    Order getOrder(Long id);

    List<OrderStatusDto> getOrderStatuses();
}
