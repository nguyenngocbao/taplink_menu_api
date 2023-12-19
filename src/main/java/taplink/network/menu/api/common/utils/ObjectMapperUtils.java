package taplink.network.menu.api.common.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtils {

    private final ModelMapper mapper;

    public <C,E> C convertEntityAndDto(final E entity, Class<C> outputClass){
        return mapper.map(entity, outputClass);
    }

    public <E, D> void mapToEntityFromDto(final E entity, final D dto){
        mapper.map(dto, entity);
    }

    public <C,E> List<C> mapAll(final Collection<? extends E> input, final Class<C> outputClass){
        return input.stream().map(
                entity -> convertEntityAndDto(entity, outputClass)
        ).collect(Collectors.toList());
    }

}
