package taplink.network.menu.api.dtos.request;

import lombok.Data;

@Data
public class ItemRequestDto {
    private String name;
    private String description;
    private Integer sortOrder;
    private Integer priceTypeId;
    private Long categoryId;
    private String priceInfo;
}
