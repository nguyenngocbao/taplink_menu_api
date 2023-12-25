package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import taplink.network.menu.api.commons.utils.FileUtils;
import taplink.network.menu.api.services.FileService;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String name) {
        InputStream fileStream = fileService.downloadFile(name);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + name);

        // Determine the content type based on the file name or other criteria
        String contentType = determineContentType(name);
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        InputStreamResource inputStreamResource = new InputStreamResource(fileStream);

        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    private String determineContentType(String fileName) {
        String fileExtension = FileUtils.getFileExtensionWithTimestamp(fileName);

        return switch (fileExtension.toLowerCase()) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            case "gif" -> MediaType.IMAGE_GIF_VALUE;
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
    }
}
