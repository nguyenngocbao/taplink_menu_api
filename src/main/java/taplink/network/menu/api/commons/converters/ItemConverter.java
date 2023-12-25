package taplink.network.menu.api.commons.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.ItemRequestDto;
import taplink.network.menu.api.dtos.response.ItemResponseDto;
import taplink.network.menu.api.models.Category;
import taplink.network.menu.api.models.Item;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemConverter {

    private final ImageConverter imageConverter;
    private final ObjectMapperUtils objectMapperUtils;

    public Item convertToNewEntityFromDto(ItemRequestDto dto, Category category, String imageName) {
        Item item = objectMapperUtils.convertEntityAndDto(dto, Item.class);
        item.setCategory(category);
        item.setImage(imageName);
        return item;
    }

    public Item convertToPersistedEntityFromDto(Item item, ItemRequestDto dto, Category category, String imageName) {
        objectMapperUtils.convertToPersistedEntityFromDto(item, dto);
        item.setCategory(category);
        item.setImage(imageName);
        return item;
    }

    public List<ItemResponseDto> convertToDtoFromEntity(List<Item> items) {
        return items.stream().map(item -> {
            ItemResponseDto itemResponseDto = objectMapperUtils.convertEntityAndDto(item, ItemResponseDto.class);
            itemResponseDto.setCategoryId(item.getCategory().getId());
            if (item.getImage() != null) {
                itemResponseDto.setImage(imageConverter.getImageUrlFromName(item.getImage()));
            }
            return itemResponseDto;
        }).collect(Collectors.toList());
    }

}
