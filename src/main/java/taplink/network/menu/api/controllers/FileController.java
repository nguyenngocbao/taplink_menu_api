package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taplink.network.menu.api.commons.utils.FileUtils;
import taplink.network.menu.api.dtos.request.DeleteImageRequestDto;
import taplink.network.menu.api.services.CategoryService;
import taplink.network.menu.api.services.FileService;
import taplink.network.menu.api.services.ItemService;
import taplink.network.menu.api.services.StoreService;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    private final ItemService itemService;

    private final StoreService storeService;

    private final CategoryService categoryService;

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        InputStream fileStream = fileService.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);

        // Determine the content type based on the file name or other criteria
        String contentType = FileUtils.determineContentType(fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        InputStreamResource inputStreamResource = new InputStreamResource(fileStream);

        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @DeleteMapping("image/")
    public void deleteImage(@RequestBody DeleteImageRequestDto deleteImageRequestDto) {
        switch (deleteImageRequestDto.getType()) {
            case MENU_ITEM:
                itemService.deleteImage(deleteImageRequestDto.getId());
                break;
            case STORE:
                storeService.deleteImage(deleteImageRequestDto.getId());
                break;
            case CATEGORY:
                categoryService.deleteImage(deleteImageRequestDto.getId());
                break;
        }
    }

}
