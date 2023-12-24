package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.LoginRequestDto;
import taplink.network.menu.api.dtos.request.OtpRequestDto;

public interface LoginService {
    void sendOTP(OtpRequestDto otpRequestDto);
    String login(LoginRequestDto loginRequestDto);
}
