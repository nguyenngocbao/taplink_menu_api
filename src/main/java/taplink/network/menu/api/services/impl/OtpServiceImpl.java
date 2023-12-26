package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.repositories.UserRepository;
import taplink.network.menu.api.services.OtpService;

import java.security.SecureRandom;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    private final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
    private final int OTP_LENGTH = 6;

    @Override
    public String generateOTP(String email) {
        User user = getUserByEmail(email);
        String otp = generateOtp(OTP_LENGTH);
        String encodedOTP = passwordEncoder.encode(otp);
        user.setOneTimePassword(encodedOTP);
        user.setOtpRequestedTime(new Date());
        userRepository.save(user);
        return otp;
    }

    @Override
    public boolean validateOTP(String email, String otp) {
        User user = getUserByEmail(email);
        if (user.getOneTimePassword() == null) {
            return false;
        }
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = user.getOtpRequestedTime().getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            clearOTP(user);
            return false;
        }
        if (!this.passwordEncoder.matches(otp, user.getOneTimePassword())) {
            return false;
        }
        clearOTP(user);
        return true;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByUsernameOrEmail(email, email).orElseThrow(() -> new ResourceNotFoundException("User could not be found for username or email: " + email));
    }

    private void clearOTP(User user) {
        user.setOneTimePassword(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }

    private String generateOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Appends a random number from 0 to 9
        }

        return otp.toString();
    }

}
