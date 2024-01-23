package taplink.network.menu.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Order;
import taplink.network.menu.api.repositories.OrderRepository;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class OrderPermissionEvaluator implements PermissionEvaluator, CustomPermissionEvaluator {

    private final OrderRepository orderRepository;
    private final StorePermissionEvaluator storePermissionEvaluator;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (targetId == null) {
            return false;
        }
        Long orderId = (Long) targetId;
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        Long storeId = order.getStore().getId();
        return storePermissionEvaluator.hasPermission(authentication, storeId, storePermissionEvaluator.getTargetType(), permission);
    }

    @Override
    public String getTargetType() {
        return "ORDER";
    }

}
