package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.response.CityResponseDto;
import taplink.network.menu.api.dtos.response.DistrictResponseDto;
import taplink.network.menu.api.dtos.response.WardResponseDto;

import java.util.List;

public interface AddressService {

    List<CityResponseDto> getAllCities();

    List<DistrictResponseDto> getAllDistrictsByCity(Long cityId);

    List<WardResponseDto> getAllWardsByDistrict(Long districtId);

}
