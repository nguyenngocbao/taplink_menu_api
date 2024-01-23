package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.dtos.request.OrderRequestDto;
import taplink.network.menu.api.dtos.response.OrderResponseDto;
import taplink.network.menu.api.dtos.response.OrderStatusDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> searchOrders(
            Authentication authentication,
            @RequestParam(value = "storeId") Long storeId,
            @RequestParam(value = "statusId", required = false) Integer statusId,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        ResponseDto<OrderResponseDto> responseDTO = orderService.searchOrders(storeId, statusId, fromDate, toDate, pageNo, pageSize, sortBy, sortDir, username);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.findById(orderId);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @GetMapping("/order-status")
    public ResponseEntity<?> getOrderStatus() {
        List<OrderStatusDto> orderStatues = orderService.getOrderStatuses();
        return new ResponseEntity<>(orderStatues, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{orderId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long orderId, @RequestBody Integer orderStatusId) {
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, orderStatusId);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }
}
