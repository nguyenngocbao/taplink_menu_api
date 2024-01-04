package taplink.network.menu.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Permission;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.models.UserStoreRole;
import taplink.network.menu.api.repositories.UserRepository;

import java.io.Serializable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StorePermissionEvaluator implements PermissionEvaluator, CustomPermissionEvaluator {

    private final UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (targetId == null) {
            return false;
        }
        Long storeId = (Long) targetId;
        return checkStorePermission(authentication, permission, storeId);
    }

    private boolean checkStorePermission(Authentication authentication, Object permission, Long storeId) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new ResourceNotFoundException("User could not be found for username or email" + username));
        List<UserStoreRole> userStoreRoles = user.getUserStoreRoles().stream().filter(userStoreRole -> userStoreRole.getStore().getId().equals(storeId)).toList();
        for (UserStoreRole userStoreRole : userStoreRoles) {
            for (Permission p : userStoreRole.getRole().getPermissions()) {
                if (permission.equals(p.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTargetType() {
        return "STORE";
    }

}
