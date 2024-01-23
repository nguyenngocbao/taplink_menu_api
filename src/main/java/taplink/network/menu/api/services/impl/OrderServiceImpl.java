package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.converters.OrderConverter;
import taplink.network.menu.api.commons.converters.OrderItemConverter;
import taplink.network.menu.api.commons.enums.OrderStatus;
import taplink.network.menu.api.commons.utils.PageableUtils;
import taplink.network.menu.api.dtos.request.OrderItemRequestDto;
import taplink.network.menu.api.dtos.request.OrderRequestDto;
import taplink.network.menu.api.dtos.response.OrderResponseDto;
import taplink.network.menu.api.dtos.response.OrderStatusDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.exceptions.AccessDeniedException;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Item;
import taplink.network.menu.api.models.Order;
import taplink.network.menu.api.models.OrderItem;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.repositories.ItemRepository;
import taplink.network.menu.api.repositories.OrderRepository;
import taplink.network.menu.api.repositories.StoreRepository;
import taplink.network.menu.api.services.OrderService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public ResponseDto<OrderResponseDto> searchOrders(Long storeId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate, int pageNo, int pageSize, String sortBy, String sortDir, String userName) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Order> orders = orderRepository.searchOrders(storeId, statusId, fromDate, toDate, pageable, userName);
        List<Order> listOfOrders = orders.getContent();
        List<OrderResponseDto> content = orderConverter.convertToDtoFromEntity(listOfOrders);
        return new ResponseDto<>(orders, content);
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Store store = getStore(orderRequestDto);
        Order order = new Order();
        order.setStore(store);
        order.setOrderStatusId(1); // created

        for (OrderItemRequestDto orderItemRequestDto : orderRequestDto.getOrderItemRequestDtos()) {
            Item item = getItem(orderItemRequestDto);
            if (!Objects.equals(item.getCategory().getStore().getId(), store.getId())) {
                throw new AccessDeniedException(String.format("Item with id '%s' is not belong to the menu with id '%s'", item.getId(), store.getId()));
            }
            OrderItem orderItem = orderItemConverter.convertToNewEntityFromDto(orderItemRequestDto, item);
            order.addOrderItem(orderItem);
        }
        Order savedOrder = orderRepository.save(order);
        OrderResponseDto orderResponseDto = getOrderResponseDto(savedOrder);
        sendNotification(orderResponseDto);
        return orderResponseDto;
    }

    @Override
    public OrderResponseDto findById(Long id) {
        Order order = getOrder(id);
        return getOrderResponseDto(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, Integer orderStatusId) {
        Order order = getOrder(id);
        if (Arrays.binarySearch(OrderStatus.getNextStatusesById(order.getOrderStatusId()), orderStatusId) < 0) {
            throw new IllegalStateException(String.format("Can not update from order status '%s' to order status '%s' of order with id '%s'", OrderStatus.getOrderStatusNameById(order.getOrderStatusId()), OrderStatus.getOrderStatusNameById(orderStatusId), order.getId()));
        }
        order.setOrderStatusId(orderStatusId);
        Order savedOrder = orderRepository.save(order);

        OrderResponseDto orderResponseDto = getOrderResponseDto(savedOrder);
        if (OrderStatus.PAYMENT_PENDING.getId() == orderStatusId) {
            sendNotification(orderResponseDto);
        }
        return orderResponseDto;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    @Override
    public List<OrderStatusDto> getOrderStatuses() {
        return Arrays.stream(OrderStatus.values()).map(orderStatus -> new OrderStatusDto(orderStatus.getId(), orderStatus.name(), orderStatus.getName())).toList();
    }

    private OrderResponseDto getOrderResponseDto(Order savedOrder) {
        return orderConverter.convertToDtoFromEntity(Collections.singletonList(savedOrder)).get(0);
    }

    private Store getStore(OrderRequestDto orderRequestDto) {
        return storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store", orderRequestDto.getStoreId()));
    }

    private Item getItem(OrderItemRequestDto orderItemRequestDto) {
        return itemRepository.findById(orderItemRequestDto.getItemId()).orElseThrow(() -> new ResourceNotFoundException("Item", orderItemRequestDto.getItemId()));
    }

    private void sendNotification(OrderResponseDto orderResponseDto) {
        messagingTemplate.convertAndSend("/topic/orderNotification", orderResponseDto);
    }
}
