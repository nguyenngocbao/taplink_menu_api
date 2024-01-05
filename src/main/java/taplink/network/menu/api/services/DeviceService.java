package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.CategoryRequestDto;
import taplink.network.menu.api.dtos.request.DeviceRequestDto;
import taplink.network.menu.api.dtos.response.CategoryResponseDto;
import taplink.network.menu.api.dtos.response.DeviceResponseDto;

import java.util.List;

public interface DeviceService {
    DeviceResponseDto getDeviceByUuid(String uuid);

    DeviceResponseDto createDevice(DeviceRequestDto deviceRequestDto);
}
