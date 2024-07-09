package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.utils.JwtUtils;
import taplink.network.menu.api.dtos.email.EmailId;
import taplink.network.menu.api.dtos.email.EmailRequestDto;
import taplink.network.menu.api.dtos.email.EmailTemplate;
import taplink.network.menu.api.dtos.email.templates.OtpEmailTemplate;
import taplink.network.menu.api.dtos.request.LoginRequestDto;
import taplink.network.menu.api.dtos.request.OtpRequestDto;
import taplink.network.menu.api.dtos.response.LoginResponseDto;
import taplink.network.menu.api.dtos.response.UserResponseDto;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.security.OtpAuthenticationToken;
import taplink.network.menu.api.services.LoginService;
import taplink.network.menu.api.services.NotificationService;
import taplink.network.menu.api.services.OtpService;
import taplink.network.menu.api.services.UserService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final OtpService otpService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;

    @Override
    public void sendOTP(OtpRequestDto otpRequestDto) {
        logger.info("Start generating and sending otp to email {}", otpRequestDto.email());
        User user = userService.findByUsernameOrEmail(otpRequestDto.email(), otpRequestDto.email());
        String otp = otpService.generateOTP(user.getEmail());
        List<EmailId> toEmailIds = Collections.singletonList(new EmailId(user.getFullName(), user.getEmail()));
        EmailTemplate emailTemplate = new OtpEmailTemplate(otp);
        EmailRequestDto emailRequestDto = EmailRequestDto.builder()
                .tos(toEmailIds)
                .subject("TapLink Menu OTP")
                .emailTemplate(emailTemplate).build();
        notificationService.sendNotification(emailRequestDto);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new OtpAuthenticationToken(loginRequestDto.email(), loginRequestDto.password()));
        String token = JwtUtils.generateToken(loginRequestDto.email());
        UserResponseDto user = userService.getUserProfile(loginRequestDto.email());
        return new LoginResponseDto(user, token);
    }
}
