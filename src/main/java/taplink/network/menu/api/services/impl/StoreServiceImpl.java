package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.constants.AppConstants;
import taplink.network.menu.api.commons.converters.StoreConverter;
import taplink.network.menu.api.commons.enums.MenuTemplate;
import taplink.network.menu.api.commons.enums.StoreTemplate;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.commons.utils.PageableUtils;
import taplink.network.menu.api.dtos.request.StoreRequestDto;
import taplink.network.menu.api.dtos.response.*;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.models.StoreType;
import taplink.network.menu.api.models.Ward;
import taplink.network.menu.api.repositories.StoreRepository;
import taplink.network.menu.api.repositories.StoreTypeRepository;
import taplink.network.menu.api.repositories.WardRepository;
import taplink.network.menu.api.services.FileService;
import taplink.network.menu.api.models.*;
import taplink.network.menu.api.repositories.*;
import taplink.network.menu.api.services.StoreService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final FileService fileService;
    private final StoreRepository storeRepository;
    private final WardRepository wardRepository;
    private final StoreTypeRepository storeTypeRepository;
    private final StoreConverter storeConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserStoreRoleRepository userStoreRoleRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public ResponseDto<StoreResponseDto> searchStores(String searchKey, int pageNo, int pageSize, String sortBy, String sortDir, String username) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Store> stores = storeRepository.searchStores(searchKey, pageable, username);
        List<Store> listOfStores = stores.getContent();
        List<StoreResponseDto> content = storeConverter.convertToDtoFromEntity(listOfStores);
        return new ResponseDto<>(stores, content);
    }

    @Transactional
    @Override
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, String username) {
        User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new ResourceNotFoundException("User could not be found for username or email" + username));
        Role adminRole = roleRepository.findByCode(AppConstants.ADMIN_ROLE).orElseThrow(() -> new ResourceNotFoundException("Role Admin could not be found"));
        Ward ward = getWard(storeRequestDto);
        StoreType storeType = getStoreType(storeRequestDto);
        String imageName = "";
        if (storeRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(storeRequestDto.getImage());
        }

        Store store = storeConverter.convertToNewEntityFromDto(storeRequestDto, ward, storeType, imageName);
        store.setOwner(user);
        Store savedStore = storeRepository.save(store);
        UserStoreRole userStoreRole = UserStoreRole.builder()
                .role(adminRole)
                .user(user)
                .store(savedStore)
                .build();
        userStoreRoleRepository.save(userStoreRole);
        return getStoreResponseDto(savedStore);
    }

    @Override
    public StoreResponseDto findById(Long id) {
        Store store = getStore(id);
        return getStoreResponseDto(store);
    }

    @Override
    public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto) {
        Ward ward = getWard(storeRequestDto);
        StoreType storeType = getStoreType(storeRequestDto);
        Store store = getStore(id);
        String imageName = store.getImage();
        if (storeRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(storeRequestDto.getImage());
            fileService.deleteFile(store.getImage()); // delete old file after upload new image successfully
        }
        store = storeConverter.convertToPersistedEntityFromDto(store, storeRequestDto, ward, storeType, imageName);
        Store savedStore = storeRepository.save(store);
        return getStoreResponseDto(savedStore);
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStore(id);
        store.setActive(false);
        fileService.deleteFile(store.getImage());
        store.setImage(null);
        storeRepository.save(store);
    }

    @Override
    public Store getStore(Long id) {
        return storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store", id));
    }

    @Override
    public List<StoreTypeResponseDto> getStoreTypes() {
        List<StoreType> storeTypes = storeTypeRepository.findAll();
        return objectMapperUtils.mapAll(storeTypes, StoreTypeResponseDto.class);

    }

    @Override
    public List<StoreTemplateDto> getStoreTemplates() {
        return Arrays.stream(StoreTemplate.values()).map(storeTemplate -> new StoreTemplateDto(storeTemplate.getId(), storeTemplate.name(), storeTemplate.getName())).toList();
    }

    @Override
    public List<MenuTemplateDto> getMenuTemplates() {
        return Arrays.stream(MenuTemplate.values()).map(storeTemplate -> new MenuTemplateDto(storeTemplate.getId(), storeTemplate.name(), storeTemplate.getName())).toList();
    }

    private StoreResponseDto getStoreResponseDto(Store savedStore) {
        return storeConverter.convertToDtoFromEntity(Collections.singletonList(savedStore)).get(0);
    }

    private StoreType getStoreType(StoreRequestDto storeRequestDto) {
        return storeTypeRepository.findById(storeRequestDto.getStoreTypeId()).orElseThrow(() -> new ResourceNotFoundException("Store type", storeRequestDto.getStoreTypeId()));
    }

    private Ward getWard(StoreRequestDto storeRequestDto) {
        return wardRepository.findById(storeRequestDto.getWardId()).orElseThrow(() -> new ResourceNotFoundException("Ward", storeRequestDto.getWardId()));
    }

}
