package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardResponseDto {
    private Long id;
    private String name;
    private String code;
    private Long districtId;
}
