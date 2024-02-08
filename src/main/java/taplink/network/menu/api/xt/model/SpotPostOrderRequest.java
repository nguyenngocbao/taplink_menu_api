package taplink.network.menu.api.xt.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpotPostOrderRequest {
    private String symbol;
    private String side;
    private String type;
    private String timeInForce;

    private String bizType;
    private String price;

    private String quantity;
}
