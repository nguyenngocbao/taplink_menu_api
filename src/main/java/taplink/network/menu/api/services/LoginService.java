package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.LoginRequestDto;
import taplink.network.menu.api.dtos.request.OtpRequestDto;
import taplink.network.menu.api.dtos.response.LoginResponseDto;

public interface LoginService {
    void sendOTP(OtpRequestDto otpRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
