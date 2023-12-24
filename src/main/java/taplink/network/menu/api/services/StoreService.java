package taplink.network.menu.api.services;

import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.dtos.response.StoreResponseDto;
import taplink.network.menu.api.models.Store;

public interface StoreService {

    ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    StoreResponseDto createStore(StoreRequestDto storeRequestDto, MultipartFile image);

    StoreResponseDto findById(Long id);

    StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto, MultipartFile image);

    void deleteStore(Long id);

    Store getStore(Long id);
}
