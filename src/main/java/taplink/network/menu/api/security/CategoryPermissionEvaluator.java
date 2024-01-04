package taplink.network.menu.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.*;
import taplink.network.menu.api.repositories.CategoryRepository;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class CategoryPermissionEvaluator implements PermissionEvaluator, CustomPermissionEvaluator {

    private final CategoryRepository categoryRepository;
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
        Long categoryId = (Long) targetId;
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
        Long storeId = category.getStore().getId();
        return storePermissionEvaluator.hasPermission(authentication, storeId, storePermissionEvaluator.getTargetType(), permission);
    }

    @Override
    public String getTargetType() {
        return "CATEGORY";
    }

}
