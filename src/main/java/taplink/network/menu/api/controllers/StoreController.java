package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.dtos.response.StoreResponseDto;
import taplink.network.menu.api.services.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> searchStores(
            @RequestParam(value = "searchKey", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        ResponseDto<StoreResponseDto> responseDTO = storeService.searchStores(searchKey, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createStore(@RequestPart("store") StoreRequestDto storeRequestDto, @RequestPart("image") MultipartFile image) {
        StoreResponseDto storeResponseDto = storeService.createStore(storeRequestDto, image);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateStore(@PathVariable("id") Long id, @RequestPart("store") StoreRequestDto storeRequestDto, @RequestPart("image") MultipartFile image) {
        StoreResponseDto storeResponseDto = storeService.updateStore(id, storeRequestDto, image);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<?> findStoreById(@PathVariable("storeId") Long id) {
        StoreResponseDto storeResponseDto = storeService.findById(id);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStoreById(@PathVariable("storeId") Long id) {
        storeService.deleteStore(id);
        return new ResponseEntity<>("Deleted store successfully", HttpStatus.OK);
    }


}
