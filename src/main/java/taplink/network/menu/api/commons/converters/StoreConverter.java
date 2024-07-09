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
import java.util.Optional;
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
            final StoreResponseDto storeResponseDto = objectMapperUtils.convertEntityAndDto(store, StoreResponseDto.class);
            if (Strings.isNotEmpty(store.getImage())) {
                storeResponseDto.setImage(FileUtils.getImageUrl(store.getImage()));
            }
            final StringBuilder addressBuilder = new StringBuilder(store.getAddress());
            String fullAddress = Optional.ofNullable(store.getWard())
                    .map(ward -> {
                        storeResponseDto.setWardId(ward.getId());
                        addressBuilder.append(", ").append(ward.getName());
                        return ward.getDistrict();
                    }).
                    map(district -> {
                        storeResponseDto.setDistrictId(district.getId());
                        addressBuilder.append(", ").append(district.getName());
                        return district.getCity();
                    })
                    .map(city -> {
                        storeResponseDto.setCityId(city.getId());
                        addressBuilder.append(", ").append(city.getName());
                        return addressBuilder.toString();
                    })
                    .orElse(addressBuilder.toString());
            storeResponseDto.setFullAddress(fullAddress);
            storeResponseDto.setPhone(store.getOwner().getPhone());
            storeResponseDto.setStoreTypeId(store.getStoreType().getId());
            storeResponseDto.setStoreOwnerId(store.getOwner().getId());
            return storeResponseDto;
        }).collect(Collectors.toList());
    }

}
