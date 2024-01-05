package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import taplink.network.menu.api.commons.utils.FileUtils;
import taplink.network.menu.api.services.FileService;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

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
    
}
