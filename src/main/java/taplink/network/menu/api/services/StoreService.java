package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.MenuTemplateDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.dtos.response.StoreResponseDto;
import taplink.network.menu.api.dtos.response.StoreTemplateDto;
import taplink.network.menu.api.dtos.response.StoreTypeResponseDto;
import taplink.network.menu.api.models.Store;

import java.util.List;

public interface StoreService {

    ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir, Long userId);

    ResponseDto<StoreResponseDto> searchAllStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    StoreResponseDto createStore(StoreRequestDto storeRequestDto, String username);

    StoreResponseDto findById(Long id);

    StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto);

    void deleteStore(Long id);

    void deleteImage(Long id);

    Store getStore(Long id);

    List<StoreTypeResponseDto> getStoreTypes();

    List<StoreTemplateDto> getStoreTemplates();

    List<MenuTemplateDto> getMenuTemplates();
}
