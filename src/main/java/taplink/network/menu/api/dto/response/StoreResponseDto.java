package taplink.network.menu.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String image;
    private String wifiPass;
    private Integer templateId;
    private String address;
    private Integer wardId;
    private Integer districtId;
    private Integer cityId;
    private Integer storeTypeId;
}
