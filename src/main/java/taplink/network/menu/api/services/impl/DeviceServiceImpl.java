package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.DeviceRequestDto;
import taplink.network.menu.api.dtos.response.DeviceResponseDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Device;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.repositories.DeviceRepository;
import taplink.network.menu.api.services.DeviceService;
import taplink.network.menu.api.services.StoreService;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final StoreService storeService;
    private final DeviceRepository deviceRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public DeviceResponseDto createDevice(DeviceRequestDto deviceRequestDto) {
        Store store = storeService.getStore(deviceRequestDto.getStoreId());
        Device device = objectMapperUtils.convertEntityAndDto(deviceRequestDto, Device.class);
        device.setStore(store);
        Device savedDevice = deviceRepository.save(device);
        return getDeviceResponseDto(savedDevice);
    }

    @Override
    public DeviceResponseDto getDeviceByUuid(String uuid) {
        Device device = getDevice(uuid);
        return getDeviceResponseDto(device);
    }

    private Device getDevice(String uuid) {
        return deviceRepository.findByUuid(uuid)
                .filter(Device::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Device", uuid));
    }

    private DeviceResponseDto getDeviceResponseDto(Device savedDevice) {
        DeviceResponseDto deviceResponseDto = objectMapperUtils.convertEntityAndDto(savedDevice, DeviceResponseDto.class);
        deviceResponseDto.setStoreId(savedDevice.getStore().getId());
        return deviceResponseDto;
    }

}
