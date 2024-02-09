package taplink.network.menu.api.xt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.xt.model.SpotPostOrderRequest;
import taplink.network.menu.api.xt.model.XtAccount;
import taplink.network.menu.api.xt.utils.XtAccountProperties;
import taplink.network.menu.api.xt.utils.XtHttpUtil;

import java.util.Stack;

@Component
public class XtService {

    public static boolean buy = true;
    public static boolean accountBuy = true;
    public static boolean accountSell = true;

    @Autowired
    private XtAccountProperties accountProperties;

    private Stack<String> account1 = new Stack<>();
    private Stack<String> account2 = new Stack<>();
    // Execute every 15 minutes
    @Scheduled(cron = "0 */2 * * * ?")
    public void runCronJob() throws JsonProcessingException {

        if (buy){
            buy();
            buy = false;
        }else {
            sell();
            buy = true;
        }
    }

    private void buy() throws JsonProcessingException {
        String uri = "/v4/order";

        SpotPostOrderRequest request = new SpotPostOrderRequest();
        request.setSymbol("retro_usdt");
        request.setSide("BUY");
        request.setType("LIMIT");
        request.setTimeInForce("GTC");
        request.setBizType("SPOT");
        request.setPrice("0.00178");
        request.setQuantity("50000");
        ObjectMapper mapper = new ObjectMapper();

        XtAccount account = new XtAccount();
        if (accountBuy){
            account.setAppKey(accountProperties.getAppKey1());
            account.setPrivateKey(accountProperties.getPrivateKey1());
        }else{
            account.setAppKey(accountProperties.getAppKey2());
            account.setPrivateKey(accountProperties.getPrivateKey2());
        }
        accountBuy = !accountBuy;


        String result = XtHttpUtil.post(account,uri,mapper.writeValueAsString(request));


    }

    private void sell() throws JsonProcessingException {
        String uri = "/v4/order";

        SpotPostOrderRequest request = new SpotPostOrderRequest();
        request.setSymbol("retro_usdt");
        request.setSide("SELL");
        request.setType("LIMIT");
        request.setTimeInForce("GTC");
        request.setBizType("SPOT");
        request.setPrice("0.00178");
        request.setQuantity("50000");
        ObjectMapper mapper = new ObjectMapper();

        XtAccount account = new XtAccount();
        if (accountSell){
            account.setAppKey(accountProperties.getAppKey1());
            account.setPrivateKey(accountProperties.getPrivateKey1());
        }else{
            account.setAppKey(accountProperties.getAppKey2());
            account.setPrivateKey(accountProperties.getPrivateKey2());
        }
        accountSell = !accountSell;

        String result = XtHttpUtil.post(account,uri,mapper.writeValueAsString(request));

    }

}
