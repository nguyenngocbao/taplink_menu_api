package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import taplink.network.menu.api.commons.converters.ItemConverter;
import taplink.network.menu.api.commons.enums.PriceType;
import taplink.network.menu.api.commons.utils.PageableUtils;
import taplink.network.menu.api.dtos.request.ItemRequestDto;
import taplink.network.menu.api.dtos.response.ItemResponseDto;
import taplink.network.menu.api.dtos.response.PriceTypeDto;
import taplink.network.menu.api.dtos.response.ResponseDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Category;
import taplink.network.menu.api.models.Item;
import taplink.network.menu.api.repositories.CategoryRepository;
import taplink.network.menu.api.repositories.ItemRepository;
import taplink.network.menu.api.services.FileService;
import taplink.network.menu.api.services.ItemService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final FileService fileService;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemConverter itemConverter;

    @Override
    public ResponseDto<ItemResponseDto> searchItems(Long categoryId, String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Item> items = itemRepository.searchItems(categoryId, searchKey, pageable);
        List<Item> listOfItems = items.getContent();
        List<ItemResponseDto> content = itemConverter.convertToDtoFromEntity(listOfItems);
        return new ResponseDto<>(items, content);
    }

    @Override
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto) {
        Category category = getCategory(itemRequestDto);
        String imageName = "";
        if (itemRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(itemRequestDto.getImage());
        }

        Item item = itemConverter.convertToNewEntityFromDto(itemRequestDto, category, imageName);
        Item savedItem = itemRepository.save(item);
        return getItemResponseDto(savedItem);
    }

    @Override
    public ItemResponseDto findById(Long id) {
        Item item = getItem(id);
        return getItemResponseDto(item);
    }

    @Override
    public ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto) {
        Category category = getCategory(itemRequestDto);
        Item item = getItem(id);
        String imageName = item.getImage();
        if (itemRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(itemRequestDto.getImage());
            fileService.deleteFile(item.getImage()); // delete old file after upload new image successfully
        }
        item = itemConverter.convertToPersistedEntityFromDto(item, itemRequestDto, category, imageName);
        Item savedItem = itemRepository.save(item);
        return getItemResponseDto(savedItem);
    }

    @Override
    public void deleteItem(Long id) {
        Item item = getItem(id);
        item.setActive(false);
        fileService.deleteFile(item.getImage());
        item.setImage(null);
        itemRepository.save(item);
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .filter(Item::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Item", id));
    }

    @Override
    public List<PriceTypeDto> getPriceTypes() {
        return Arrays.stream(PriceType.values()).map(priceType -> new PriceTypeDto(priceType.getId(), priceType.name(), priceType.getName())).toList();
    }

    private ItemResponseDto getItemResponseDto(Item savedItem) {
        return itemConverter.convertToDtoFromEntity(Collections.singletonList(savedItem)).get(0);
    }

    private Category getCategory(ItemRequestDto itemRequestDto) {
        return categoryRepository.findById(itemRequestDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", itemRequestDto.getCategoryId()));
    }

}
