package taplink.network.menu.api.dtos.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemRequestDto {
    private String name;
    private String description;
    private Integer sortOrder;
    private Integer priceTypeId;
    private Long categoryId;
    private String priceInfo;
    private MultipartFile image;
}
