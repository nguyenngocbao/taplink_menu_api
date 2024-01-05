package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Integer sortOrder;
    private Integer priceTypeId;
    private Long categoryId;
    private String priceInfo;
}
