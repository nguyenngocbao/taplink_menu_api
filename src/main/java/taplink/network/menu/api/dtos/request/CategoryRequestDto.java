package taplink.network.menu.api.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    private Long storeId;
    private String name;
    private String description;
    private Integer templateId;
}
