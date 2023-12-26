package taplink.network.menu.api.services;

public interface OtpService {
    String generateOTP(String key);
    boolean validateOTP(String key, String otp);
}
