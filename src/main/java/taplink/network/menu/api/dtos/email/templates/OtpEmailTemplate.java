package taplink.network.menu.api.dtos.email.templates;

import lombok.*;
import taplink.network.menu.api.dtos.email.EmailTemplate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpEmailTemplate extends EmailTemplate {

    private String otpCode;

    @Override
    public String getTemplateName() {
        return "otp-email";
    }
}
