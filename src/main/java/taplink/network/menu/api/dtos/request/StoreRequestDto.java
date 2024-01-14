package taplink.network.menu.api.dtos.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StoreRequestDto {
    private String name;
    private String wifiPass;
    private Integer storeTemplateId;
    private Integer menuTemplateId;
    private String address;
    private Long wardId;
    private Long storeTypeId;
    private MultipartFile image;
}
