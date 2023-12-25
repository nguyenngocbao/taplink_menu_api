package taplink.network.menu.api.services.impl;

import com.google.common.base.Strings;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.services.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MinIOFileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(MinIOFileServiceImpl.class);

    private MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.credentials.access-key}")
    private String accessKey;

    @Value("${minio.credentials.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    private void initClient() {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            LocalDateTime timestamp = LocalDateTime.now();
            String object = file.getOriginalFilename() + AppConstants.DASH + timestamp;

            // Create a temporary file from the MultipartFile
            File tempFile = File.createTempFile(AppConstants.TEMP_FOLDER, null);
            file.transferTo(tempFile);

            // Upload the temporary file to MinIO
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(object)
                            .filename(tempFile.getAbsolutePath()) // Use the temporary file path
                            .build());

            // Delete the temporary file after uploading
            tempFile.delete();

            return object;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("Error occurred during image upload: {}", e.getMessage());
        }
        return AppConstants.EMPTY;
    }

    @Override
    public InputStream downloadFile(String fileName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("Error occurred during image download: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteFile(String fileName) {
        if (Strings.isNullOrEmpty(fileName)) {
            return false;
        }

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
            return true;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("Error occurred during delete image: {}", e.getMessage());
        }
        return false;
    }
}
