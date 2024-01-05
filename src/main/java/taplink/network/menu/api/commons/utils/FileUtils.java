package taplink.network.menu.api.commons.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import taplink.network.menu.api.controllers.FileController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUtils {

    public String getFileExtensionWithTimestamp(String fileName) {
        // Define a regular expression pattern to match the image name and file extension
        Pattern pattern = Pattern.compile("(.+?)(\\.[^.]+)-\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.matches()) {
            return matcher.group(2).substring(1);
        } else {
            return null;
        }
    }

    public String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return ""; // No file extension found
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    public String getImageUrl(String imageName) {
        return MvcUriComponentsBuilder.fromMethodName(FileController.class, "getImage", imageName).build().toUriString();
    }

    public String determineContentType(String fileName) {
        String fileExtension = FileUtils.getFileExtensionWithTimestamp(fileName);

        return switch (fileExtension.toLowerCase()) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            case "gif" -> MediaType.IMAGE_GIF_VALUE;
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
    }

}