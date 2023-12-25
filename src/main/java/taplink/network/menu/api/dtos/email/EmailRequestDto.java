package taplink.network.menu.api.dtos.email;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
    private String subject;
    private EmailId from;
    private List<EmailId> tos;
    private List<EmailId> ccs;
    private EmailTemplate emailTemplate;
}
