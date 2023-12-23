package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.converters.StoreConverter;
import taplink.network.menu.api.commons.utils.PageableUtils;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.dtos.response.StoreResponseDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.models.StoreType;
import taplink.network.menu.api.models.Ward;
import taplink.network.menu.api.repositories.StoreRepository;
import taplink.network.menu.api.repositories.StoreTypeRepository;
import taplink.network.menu.api.repositories.WardRepository;
import taplink.network.menu.api.services.StoreService;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;
    private final WardRepository wardRepository;
    private final StoreTypeRepository storeTypeRepository;
    private final StoreConverter storeConverter;

    @Override
    public ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Store> stores = storeRepository.searchStores(searchKey, pageable);
        List<Store> listOfStores = stores.getContent();
        List<StoreResponseDto> content = storeConverter.convertToDtoFromEntity(listOfStores);
        return new ResponseDto<>(stores, content);
    }

    @Override
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, MultipartFile image) {
        Ward ward = getWard(storeRequestDto);
        StoreType storeType = getStoreType(storeRequestDto);
        Store store = storeConverter.convertToNewEntityFromDto(storeRequestDto, ward, storeType);
        Store savedStore = storeRepository.save(store);
        return getStoreResponseDto(savedStore);
    }

    @Override
    public StoreResponseDto findById(Long id) {
        Store store = getStore(id);
        return getStoreResponseDto(store);
    }

    @Override
    public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto, MultipartFile image) {
        Ward ward = getWard(storeRequestDto);
        StoreType storeType = getStoreType(storeRequestDto);
        Store store = getStore(id);
        storeConverter.convertToPersistedEntityFromDto(store, storeRequestDto, ward, storeType);
        Store savedStore = storeRepository.save(store);
        return getStoreResponseDto(savedStore);
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStore(id);
        store.setActive(false);
        storeRepository.save(store);
    }

    private StoreResponseDto getStoreResponseDto(Store savedStore) {
        return storeConverter.convertToDtoFromEntity(Collections.singletonList(savedStore)).get(0);
    }

    private StoreType getStoreType(StoreRequestDto storeRequestDto) {
        return storeTypeRepository.findById(storeRequestDto.getStoreTypeId()).orElseThrow(() -> new ResourceNotFoundException("Store type", storeRequestDto.getStoreTypeId()));
    }

    private Ward getWard(StoreRequestDto storeRequestDto) {
        return wardRepository.findById(storeRequestDto.getWardId()).orElseThrow(() -> new ResourceNotFoundException("Ward", storeRequestDto.getWardId()));
    }

    private Store getStore(Long id) {
        return storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store", id));
    }

}
