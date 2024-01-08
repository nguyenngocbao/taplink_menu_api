package taplink.network.menu.api.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequestDto(
        @Schema(description = "username", example = "lmthongit98")
        @NotBlank(message = "Name cannot be blank")
        String username,

        @Schema(description = "fullName", example = "Thong Ly Minh")
        @NotBlank(message = "Name cannot be blank")
        String fullName,

        @Schema(description = "email", example = "lmthong98@gmail.com")
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @Schema(description = "phone", example = "012345678")
        @NotBlank(message = "Phone cannot be blank")
        String phone

//        @Schema(description = "password", example = "123456")
//        @NotBlank(message = "Password cannot be blank")
//        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
//        String password
) {

}
