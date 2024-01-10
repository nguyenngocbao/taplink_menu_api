package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.SignupRequestDto;
import taplink.network.menu.api.dtos.response.UserResponseDto;
import taplink.network.menu.api.models.User;

public interface UserService {
    void signup(SignupRequestDto requestDto);

    UserResponseDto getUserProfile(String username);

    User findByUsernameOrEmail(String username, String email);

}
