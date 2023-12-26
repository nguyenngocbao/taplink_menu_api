package taplink.network.menu.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taplink.network.menu.api.dtos.request.LoginRequestDto;
import taplink.network.menu.api.dtos.request.OtpRequestDto;
import taplink.network.menu.api.dtos.request.SignupRequestDto;
import taplink.network.menu.api.dtos.response.LoginResponseDto;
import taplink.network.menu.api.services.LoginService;
import taplink.network.menu.api.services.UserService;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final LoginService loginService;

    @Operation(summary = "Request sending OTP to user")
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequestDto otpRequestDto) {
        loginService.sendOTP(otpRequestDto);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @Operation(summary = "Authenticate user and return token")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String token = loginService.login(loginRequestDto);
        return ResponseEntity.ok(new LoginResponseDto(loginRequestDto.email(), token));
    }

    @Operation(summary = "Signup user")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}