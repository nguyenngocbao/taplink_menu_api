package taplink.network.menu.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.common.utils.ObjectMapperUtils;
import taplink.network.menu.api.common.utils.PageableUtils;
import taplink.network.menu.api.dto.request.StoreRequestDto;
import taplink.network.menu.api.dto.response.ResponseDto;
import taplink.network.menu.api.dto.response.StoreResponseDto;
import taplink.network.menu.api.exception.ResourceNotFoundException;
import taplink.network.menu.api.model.Store;
import taplink.network.menu.api.repository.StoreRepository;
import taplink.network.menu.api.service.StoreService;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);


    private final StoreRepository storeRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Store> stores = storeRepository.searchStores(searchKey, pageable);
        List<Store> listOfStores = stores.getContent();
        List<StoreResponseDto> content = objectMapperUtils.mapAll(listOfStores, StoreResponseDto.class);
        return new ResponseDto<>(stores, content);
    }

    @Override
    public StoreResponseDto createStore(String storeJson, MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StoreRequestDto storeRequestDto = objectMapper.readValue(storeJson, StoreRequestDto.class);
            Store store = objectMapperUtils.convertEntityAndDto(storeRequestDto, Store.class);
            Store savedStore = storeRepository.save(store);
            return objectMapperUtils.convertEntityAndDto(savedStore, StoreResponseDto.class);
        } catch (IOException ex) {
            logger.error("Getting exception while reading storeJson", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public StoreResponseDto findById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found for id: " + id));
        return objectMapperUtils.convertEntityAndDto(store, StoreResponseDto.class);
    }


}
