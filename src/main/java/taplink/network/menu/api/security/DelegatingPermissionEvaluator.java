package taplink.network.menu.api.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DelegatingPermissionEvaluator implements PermissionEvaluator {


    private final Map<String, CustomPermissionEvaluator> evaluatorMap;

    public DelegatingPermissionEvaluator(List<CustomPermissionEvaluator> evaluators) {
        this.evaluatorMap = evaluators.stream().collect(Collectors.toMap(CustomPermissionEvaluator::getTargetType, Function.identity()));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        PermissionEvaluator evaluator = (PermissionEvaluator) this.evaluatorMap.get(targetType);
        if (evaluator == null) {
            return false;
        }
        return evaluator.hasPermission(authentication, targetId, targetType, permission);
    }
}