package taplink.network.menu.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import taplink.network.menu.api.commons.enums.CategoryTemplate;
import taplink.network.menu.api.commons.utils.FileUtils;
import taplink.network.menu.api.commons.utils.ObjectMapperUtils;
import taplink.network.menu.api.dtos.request.CategoryRequestDto;
import taplink.network.menu.api.dtos.response.CategoryResponseDto;
import taplink.network.menu.api.dtos.response.CategoryTemplateDto;
import taplink.network.menu.api.exceptions.ResourceNotFoundException;
import taplink.network.menu.api.models.Category;
import taplink.network.menu.api.models.Store;
import taplink.network.menu.api.repositories.CategoryRepository;
import taplink.network.menu.api.services.CategoryService;
import taplink.network.menu.api.services.FileService;
import taplink.network.menu.api.services.StoreService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final StoreService storeService;
    private final CategoryRepository categoryRepository;
    private final ObjectMapperUtils objectMapperUtils;
    private final FileService fileService;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Store store = storeService.getStore(categoryRequestDto.getStoreId());
        Category category = objectMapperUtils.convertEntityAndDto(categoryRequestDto, Category.class);
        String imageName = "";
        if (categoryRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(categoryRequestDto.getImage());
        }
        category.setImage(imageName);
        category.setStore(store);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(Long storeId) {
        Store store = storeService.getStore(storeId);
        List<Category> categories = store.getCategories().stream().filter(Category::getActive).toList();
        return categories.stream().map(this::mapToDto).toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return mapToDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = getCategory(categoryId);
        objectMapperUtils.convertToPersistedEntityFromDto(category, categoryRequestDto);
        String imageName = "";
        if (categoryRequestDto.getImage() != null) {
            imageName = fileService.checkAndUploadImage(categoryRequestDto.getImage());
        }
        category.setImage(imageName);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        category.setActive(false);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryTemplateDto> getCategoryTemplates() {
        return Arrays.stream(CategoryTemplate.values()).map(categoryTemplate -> new CategoryTemplateDto(categoryTemplate.getId(), categoryTemplate.name(), categoryTemplate.getName())).toList();
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
    }

    private CategoryResponseDto mapToDto(Category category) {
        var categoryResponseDto = objectMapperUtils.convertEntityAndDto(category, CategoryResponseDto.class);
        if (StringUtils.hasText(category.getImage())) {
            categoryResponseDto.setImage(FileUtils.getImageUrl(category.getImage()));
        }
        return categoryResponseDto;
    }

}
