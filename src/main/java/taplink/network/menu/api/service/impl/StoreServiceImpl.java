package taplink.network.menu.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.common.utils.ObjectMapperUtils;
import taplink.network.menu.api.common.utils.PageableUtils;
import taplink.network.menu.api.dto.response.ResponseDto;
import taplink.network.menu.api.dto.response.StoreResponseDto;
import taplink.network.menu.api.model.Store;
import taplink.network.menu.api.repository.StoreRepository;
import taplink.network.menu.api.service.StoreService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

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


}
