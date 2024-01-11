package taplink.network.menu.api.commons.converters;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.commons.utils.FileUtils;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.StoreResponseDto;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.models.StoreType;
import taplink.network.menu.api.models.Ward;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoreConverter {

    private final ObjectMapperUtils objectMapperUtils;

    public Store convertToNewEntityFromDto(StoreRequestDto dto, Ward ward, StoreType storeType, String imageName) {
        Store store = objectMapperUtils.convertEntityAndDto(dto, Store.class);
        store.setWard(ward);
        store.setStoreType(storeType);
        store.setImage(imageName);
        return store;
    }

    public Store convertToPersistedEntityFromDto(Store store, StoreRequestDto dto, Ward ward, StoreType storeType, String imageName) {
        objectMapperUtils.convertToPersistedEntityFromDto(store, dto);
        store.setWard(ward);
        store.setStoreType(storeType);
        store.setImage(imageName);
        return store;
    }

    public List<StoreResponseDto> convertToDtoFromEntity(List<Store> stores) {
        return stores.stream().map(store -> {
            StoreResponseDto storeResponseDto = objectMapperUtils.convertEntityAndDto(store, StoreResponseDto.class);
            storeResponseDto.setStoreTypeId(store.getStoreType().getId());
            storeResponseDto.setWardId(store.getWard().getId());
            storeResponseDto.setDistrictId(store.getWard().getDistrict().getId());
            storeResponseDto.setCityId(store.getWard().getDistrict().getId());
            if (Strings.isNotEmpty(store.getImage())) {
                storeResponseDto.setImage(FileUtils.getImageUrl(store.getImage()));
            }
            return storeResponseDto;
        }).collect(Collectors.toList());
    }

}
