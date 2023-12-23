package taplink.network.menu.api.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.common.utils.ObjectMapperUtils;
import taplink.network.menu.api.dto.request.StoreRequestDto;
import taplink.network.menu.api.dto.response.StoreResponseDto;
import taplink.network.menu.api.model.Store;
import taplink.network.menu.api.model.StoreType;
import taplink.network.menu.api.model.Ward;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoreConverter {

    private final ObjectMapperUtils objectMapperUtils;

    public Store convertToNewEntityFromDto(StoreRequestDto dto, Ward ward, StoreType storeType) {
        Store store = objectMapperUtils.convertEntityAndDto(dto, Store.class);
        store.setWard(ward);
        store.setStoreType(storeType);
        return store;
    }

    public void convertToPersistedEntityFromDto(Store store, StoreRequestDto dto, Ward ward, StoreType storeType) {
        objectMapperUtils.convertToPersistedEntityFromDto(store, dto);
        store.setWard(ward);
        store.setStoreType(storeType);
    }

    public List<StoreResponseDto> convertToDtoFromEntity(List<Store> stores) {
        return stores.stream().map(store -> {
            StoreResponseDto storeResponseDto = objectMapperUtils.convertEntityAndDto(store, StoreResponseDto.class);
            storeResponseDto.setStoreTypeId(store.getStoreType().getId());
            storeResponseDto.setWardId(store.getWard().getId());
            storeResponseDto.setDistrictId(store.getWard().getDistrict().getId());
            storeResponseDto.setCityId(store.getWard().getDistrict().getId());
            return storeResponseDto;
        }).collect(Collectors.toList());
    }

}
