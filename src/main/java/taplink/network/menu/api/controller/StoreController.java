package taplink.network.menu.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.common.constants.AppConstants;
import taplink.network.menu.api.dto.response.ResponseDto;
import taplink.network.menu.api.dto.response.StoreResponseDto;
import taplink.network.menu.api.service.StoreService;

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

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createStore(@RequestPart("store") String storeJson, @RequestPart("image") MultipartFile image) {
        StoreResponseDto storeResponseDto = storeService.createStore(storeJson, image);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<?> findStoreById(@PathVariable("storeId") Long id) {
        StoreResponseDto storeResponseDto = storeService.findById(id);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }



}
