package taplink.network.menu.api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
}
