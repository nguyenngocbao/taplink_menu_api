package taplink.network.menu.api.dto.request;

import lombok.Data;

@Data
public class StoreRequestDto {
    private String email;
    private String name;
    private String phone;
    private boolean active;
    private String wifiPass;
    private Integer templateId;
    private String address;
    private Integer wardId;
    private Integer districtId;
    private Integer cityId;
    private Integer storeTypeId;
}
