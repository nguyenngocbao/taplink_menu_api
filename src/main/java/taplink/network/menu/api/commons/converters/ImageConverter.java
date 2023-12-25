package taplink.network.menu.api.commons.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageConverter {

    @Value("${server.base-url}")
    private String baseUrl;

    @Value("${server.image-url}")
    private String imageUrl;

    public String getImageUrlFromName(String imageName) {
        return baseUrl + imageUrl + imageName;
    }

}
