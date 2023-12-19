package taplink.network.menu.api.service;

import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.dto.request.StoreRequestDto;
import taplink.network.menu.api.dto.response.ResponseDto;
import taplink.network.menu.api.dto.response.StoreResponseDto;

public interface StoreService {

    ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    StoreResponseDto createStore(StoreRequestDto storeRequestDto, MultipartFile image);

    StoreResponseDto findById(Long id);

    StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto, MultipartFile image);
}
