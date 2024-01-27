package taplink.network.menu.api.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryRequestDto {
    private Long storeId;
    private String name;
    private String description;
    private Integer templateId;
    private MultipartFile image;
}
