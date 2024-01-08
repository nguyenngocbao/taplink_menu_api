package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.SignupRequestDto;
import taplink.network.menu.api.dtos.response.UserResponseDto;

public interface UserService {
    void signup(SignupRequestDto requestDto);

    UserResponseDto getUserProfile(String username);
}
