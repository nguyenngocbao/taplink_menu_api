package taplink.network.menu.api.services;

import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.*;
import taplink.network.menu.api.models.Store;

import java.util.List;

public interface StoreService {

    ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir, String username);

    StoreResponseDto createStore(StoreRequestDto storeRequestDto, MultipartFile image, String username);

    StoreResponseDto findById(Long id);

    StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto, MultipartFile image);

    void deleteStore(Long id);

    Store getStore(Long id);

    List<StoreTypeResponseDto> getStoreTypes();

    List<StoreTemplateDto> getStoreTemplates();

    List<MenuTemplateDto> getMenuTemplates();
}
