package taplink.network.menu.api.controllers;

import cn.hutool.json.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taplink.network.menu.api.dtos.request.DeviceRequestDto;
import taplink.network.menu.api.dtos.response.CategoryResponseDto;
import taplink.network.menu.api.xt.model.SpotPostOrderRequest;
import taplink.network.menu.api.xt.model.XtAccount;
import taplink.network.menu.api.xt.utils.XtAccountProperties;
import taplink.network.menu.api.xt.utils.XtHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/xt", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class XtController {

    @Autowired
    private XtAccountProperties accountProperties;

    @PostMapping
    public ResponseEntity<?> postOrder(@RequestBody() SpotPostOrderRequest request) throws JsonProcessingException {
        String uri = "/v4/order";
        ObjectMapper mapper = new ObjectMapper();

        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey2());
        account.setPrivateKey(accountProperties.getPrivateKey2());

        String result = XtHttpUtil.post(account,uri,mapper.writeValueAsString(request));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id) {
        String uri = "/v4/order";
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", id);
        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.get(account,uri, param), HttpStatus.OK);
    }
    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        String uri = "/v4/order/"+id;
        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.delete(account,uri, null), HttpStatus.OK);
    }

    @GetMapping("/openOrder")
    public ResponseEntity<?> openOrder() {
        String uri = "/v4/open-order";
        Map<String, Object> param = new HashMap<>();
        param.put("symbol", "retro_usdt");
        param.put("bizType", "SPOT");

        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.get(account,uri, param), HttpStatus.OK);
    }

    @GetMapping("/fullTicket")
    public ResponseEntity<?> fullTicket() {
        String uri = "/v4/public/ticker";
        Map<String, Object> param = new HashMap<>();
        param.put("symbol", "retro_usdt");
        //param.put("bizType", "SPOT");

        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.get(account,uri, param), HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        String uri = "/v4/balance";
        Map<String, Object> param = new HashMap<>();
        param.put("currency", "usdt");
        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.get(account,uri, param), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> history() {
        String uri = "/v4/history-order";
        Map<String, Object> param = new HashMap<>();
        param.put("bizType", "SPOT");
        XtAccount account = new XtAccount();
        account.setAppKey(accountProperties.getAppKey1());
        account.setPrivateKey(accountProperties.getPrivateKey1());
        return new ResponseEntity<>(XtHttpUtil.get(account,uri, param), HttpStatus.OK);
    }
}
