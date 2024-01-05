package taplink.network.menu.api.services;

import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.commons.utils.FileUtils;

import java.io.InputStream;

public interface FileService {

    default String checkAndUploadImage(MultipartFile imageFile) {
        if (!isValidImageExtension(imageFile)) {
            throw new IllegalArgumentException(imageFile.getOriginalFilename() + " is not a valid image type");
        }
        return uploadFile(imageFile);
    }

    private boolean isValidImageExtension(MultipartFile imageFile) {
        if (imageFile == null) {
            return false;
        }

        String originalFilename = imageFile.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            return false;
        }

        String fileExtension = FileUtils.getFileExtension(originalFilename);

        for (String validExtension : AppConstants.validImageExtensions) {
            if (validExtension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }

        return false;
    }

    String uploadFile(MultipartFile imageFile);

    InputStream downloadFile(String fileName);

    boolean deleteFile(String fileName);
}
