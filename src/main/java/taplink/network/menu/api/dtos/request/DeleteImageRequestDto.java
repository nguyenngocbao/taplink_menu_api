package taplink.network.menu.api.dtos.request;

import lombok.Data;
import taplink.network.menu.api.commons.enums.ImageSourceType;

@Data
public class DeleteImageRequestDto {
    private Long id;
    private ImageSourceType type;
}
