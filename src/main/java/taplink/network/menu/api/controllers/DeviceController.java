package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taplink.network.menu.api.dtos.request.DeviceRequestDto;
import taplink.network.menu.api.dtos.response.DeviceResponseDto;
import taplink.network.menu.api.services.DeviceService;

@RestController
@RequestMapping(path = "/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findDeviceById(@PathVariable("uuid") String uuid) {
        DeviceResponseDto deviceResponseDto = deviceService.getDeviceByUuid(uuid);
        return new ResponseEntity<>(deviceResponseDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createDevice(@RequestBody() DeviceRequestDto deviceRequestDto) {
        DeviceResponseDto deviceResponseDto = deviceService.createDevice(deviceRequestDto);
        return new ResponseEntity<>(deviceResponseDto, HttpStatus.CREATED);
    }

}