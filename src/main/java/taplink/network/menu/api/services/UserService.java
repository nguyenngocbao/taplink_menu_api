package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.SignupRequestDto;

public interface UserService {
    void signup(SignupRequestDto requestDto);
}
