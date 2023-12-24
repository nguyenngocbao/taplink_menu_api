package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.services.ItemService;

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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createItem(@RequestPart("item") ItemRequestDto itemRequestDto, @RequestPart("image") MultipartFile image) {
        ItemResponseDto itemResponseDto = itemService.createItem(itemRequestDto, image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(itemResponseDto, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateItem(@PathVariable("id") Long id, @RequestPart("item") ItemRequestDto itemRequestDto, @RequestPart("image") MultipartFile image) {
        ItemResponseDto itemResponseDto = itemService.updateItem(id, itemRequestDto, image);
        return new ResponseEntity<>(itemResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> findItemById(@PathVariable("itemId") Long id) {
        ItemResponseDto itemResponseDto = itemService.findById(id);
        return new ResponseEntity<>(itemResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable("itemId") Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Deleted item successfully", HttpStatus.OK);
    }

}
