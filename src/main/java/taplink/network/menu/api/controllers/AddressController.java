package taplink.network.menu.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taplink.network.menu.api.dtos.response.CityResponseDto;
import taplink.network.menu.api.dtos.response.DistrictResponseDto;
import taplink.network.menu.api.dtos.response.WardResponseDto;
import taplink.network.menu.api.services.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/cities")
    public ResponseEntity<?> getAllCategories() {
        List<CityResponseDto> responseDtoList = addressService.getAllCities();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/cities/{cityId}/districts")
    public ResponseEntity<?> getAllDistricts(@PathVariable Long cityId) {
        List<DistrictResponseDto> responseDtoList = addressService.getAllDistrictsByCity(cityId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<?> getAllWards(@PathVariable Long districtId) {
        List<WardResponseDto> responseDtoList = addressService.getAllWardsByDistrict(districtId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

}
