package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.*;
import taplink.network.menu.api.services.StoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> searchStores(Authentication authentication,
                                          @RequestParam(value = "searchKey", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
                                          @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        String username = authentication.getPrincipal().toString();
        ResponseDto<StoreResponseDto> responseDTO = storeService.searchStores(searchKey, pageNo, pageSize, sortBy, sortDir, username);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<?> findStoreById(@PathVariable("storeId") Long id) {
        StoreResponseDto storeResponseDto = storeService.findById(id);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @GetMapping("/store-type")
    public ResponseEntity<?> getStoreTypes() {
        List<StoreTypeResponseDto> storeTypeResponseDtoList = storeService.getStoreTypes();
        return new ResponseEntity<>(storeTypeResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/store-template")
    public ResponseEntity<?> getStoreTemplates() {
        List<StoreTemplateDto> storeTemplates = storeService.getStoreTemplates();
        return new ResponseEntity<>(storeTemplates, HttpStatus.OK);
    }

    @GetMapping("/menu-template")
    public ResponseEntity<?> getMenuTemplates() {
        List<MenuTemplateDto> menuTemplates = storeService.getMenuTemplates();
        return new ResponseEntity<>(menuTemplates, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createStore(Authentication authentication, @ModelAttribute StoreRequestDto storeRequestDto) {
        String username = authentication.getPrincipal().toString();
        StoreResponseDto storeResponseDto = storeService.createStore(storeRequestDto, username);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasPermission(#id, 'STORE', 'STORE_EDIT')")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateStore(@PathVariable("id") Long id, @RequestPart("store") StoreRequestDto storeRequestDto, @RequestPart(value = "image", required = false) MultipartFile image) {
        StoreResponseDto storeResponseDto = storeService.updateStore(id, storeRequestDto, image);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(#id, 'STORE', 'STORE_DELETE')")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStoreById(@PathVariable("storeId") Long id) {
        storeService.deleteStore(id);
        return new ResponseEntity<>("Deleted store successfully", HttpStatus.OK);
    }

}
