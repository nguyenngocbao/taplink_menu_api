package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.ItemRequestDto;
import taplink.network.menu.api.dtos.response.ItemResponseDto;
import taplink.network.menu.api.dtos.response.PriceTypeDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.models.Item;

import java.util.List;

public interface ItemService {

    ResponseDto<ItemResponseDto> searchItems(Long categoryId, String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    ItemResponseDto createItem(ItemRequestDto itemRequestDto);

    ItemResponseDto findById(Long id);

    ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto);

    void deleteItem(Long id);

    void deleteImage(Long id);

    Item getItem(Long id);

    List<PriceTypeDto> getPriceTypes();
}
