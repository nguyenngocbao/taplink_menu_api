package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.response.CityResponseDto;
import taplink.network.menu.api.dtos.response.DistrictResponseDto;
import taplink.network.menu.api.dtos.response.WardResponseDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.City;
import taplink.network.menu.api.models.District;
import taplink.network.menu.api.models.Ward;
import taplink.network.menu.api.repositories.CityRepository;
import taplink.network.menu.api.repositories.DistrictRepository;
import taplink.network.menu.api.repositories.WardRepository;
import taplink.network.menu.api.services.AddressService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final CityRepository cityRepository;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final ObjectMapperUtils objectMapperUtils;


    @Override
    public List<CityResponseDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return objectMapperUtils.mapAll(cities, CityResponseDto.class);
    }

    @Override
    public List<DistrictResponseDto> getAllDistrictsByCity(Long cityId) {
        getCity(cityId);
        List<District> districts = districtRepository.findAllByCityId(cityId);
        return getDistrictResponseDtoList(districts);
    }

    @Override
    public List<WardResponseDto> getAllWardsByDistrict(Long districtId) {
        getDistrict(districtId);
        List<Ward> wards = wardRepository.findAllByDistrictId(districtId);
        return getWardResponseDtoList(wards);
    }

    private List<DistrictResponseDto> getDistrictResponseDtoList(List<District> districts) {
        return districts.stream().map(district -> {
            DistrictResponseDto districtResponseDto = objectMapperUtils.convertEntityAndDto(district, DistrictResponseDto.class);
            districtResponseDto.setCityId(district.getCity().getId());
            return districtResponseDto;
        }).collect(Collectors.toList());
    }

    private List<WardResponseDto> getWardResponseDtoList(List<Ward> wards) {
        return wards.stream().map(ward -> {
            WardResponseDto wardResponseDto = objectMapperUtils.convertEntityAndDto(ward, WardResponseDto.class);
            wardResponseDto.setDistrictId(ward.getDistrict().getId());
            return wardResponseDto;
        }).collect(Collectors.toList());
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFoundException("City", cityId));
    }

    private District getDistrict(Long districtId) {
        return districtRepository.findById(districtId).orElseThrow(() -> new ResourceNotFoundException("District", districtId));
    }
}
