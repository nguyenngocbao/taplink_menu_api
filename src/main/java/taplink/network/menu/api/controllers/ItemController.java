package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.dtos.request.ItemRequestDto;
import taplink.network.menu.api.dtos.response.ItemResponseDto;
import taplink.network.menu.api.dtos.response.PriceTypeDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.services.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<?> searchItems(
            @RequestParam(value = "categoryId") Long categoryId,
            @RequestParam(value = "searchKey", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_SORT_ORDER, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        ResponseDto<ItemResponseDto> responseDTO = itemService.searchItems(categoryId, searchKey, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> findItemById(@PathVariable Long itemId) {
        ItemResponseDto itemResponseDto = itemService.findById(itemId);
        return new ResponseEntity<>(itemResponseDto, HttpStatus.OK);
    }

    @GetMapping("/price-type")
    public ResponseEntity<?> getPriceTypes() {
        List<PriceTypeDto> priceTypes = itemService.getPriceTypes();
        return new ResponseEntity<>(priceTypes, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#itemRequestDto.categoryId, 'CATEGORY', 'ITEM_CREATE')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createItem(@RequestPart("item") ItemRequestDto itemRequestDto, @RequestPart("image") MultipartFile image) {
        ItemResponseDto itemResponseDto = itemService.createItem(itemRequestDto, image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(itemResponseDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasPermission(#itemId, 'ITEM', 'ITEM_EDIT')")
    @PutMapping(value = "/{itemId}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @RequestPart("item") ItemRequestDto itemRequestDto, @RequestPart("image") MultipartFile image) {
        ItemResponseDto itemResponseDto = itemService.updateItem(itemId, itemRequestDto, image);
        return new ResponseEntity<>(itemResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#itemId, 'ITEM', 'ITEM_DELETE')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>("Deleted item successfully", HttpStatus.OK);
    }

}
