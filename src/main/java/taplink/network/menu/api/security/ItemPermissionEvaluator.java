package taplink.network.menu.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Item;
import taplink.network.menu.api.repositories.ItemRepository;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class ItemPermissionEvaluator implements PermissionEvaluator, CustomPermissionEvaluator {

    private final ItemRepository itemRepository;
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
        Long itemId = (Long) targetId;
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", itemId));
        Long storeId = item.getCategory().getStore().getId();
        return storePermissionEvaluator.hasPermission(authentication, storeId, storePermissionEvaluator.getTargetType(), permission);
    }

    @Override
    public String getTargetType() {
        return "ITEM";
    }

}
