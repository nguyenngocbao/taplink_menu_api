package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.utils.JwtUtils;
import taplink.network.menu.api.dtos.request.LoginRequestDto;
import taplink.network.menu.api.dtos.request.OtpRequestDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.repositories.UserRepository;
import taplink.network.menu.api.secutiry.OtpAuthenticationToken;
import taplink.network.menu.api.services.LoginService;
import taplink.network.menu.api.services.OtpService;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public void sendOTP(OtpRequestDto otpRequestDto) {
        User user = userRepository.findByUsernameOrEmail(otpRequestDto.email(), otpRequestDto.email()).orElseThrow(() -> new ResourceNotFoundException("User could not be found for username or email" + otpRequestDto.email()));
        String otp = otpService.generateOTP(user.getEmail());
        // send otp to user via email
        logger.info("generated otp: {}", otp);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new OtpAuthenticationToken(loginRequestDto.email(), loginRequestDto.password()));
        return JwtUtils.generateToken(loginRequestDto.email());
    }
}
