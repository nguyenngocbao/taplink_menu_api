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
    private Long storeTypeId;
    private String phone;
    private String address;
    private String fullAddress;
    private Long wardId;
    private Long cityId;
    private Long districtId;
    private Long storeOwnerId;
}
