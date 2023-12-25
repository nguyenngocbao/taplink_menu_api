package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponseDto {
    private Long id;
    private String name;
    private String image;
    private String wifiPass;
    private Integer storeTemplateId;
    private Integer menuTemplateId;
    private String address;
    private Long wardId;
    private Long districtId;
    private Long cityId;
    private Long storeTypeId;
}
