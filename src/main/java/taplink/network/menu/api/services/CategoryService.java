package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.CategoryRequestDto;
import taplink.network.menu.api.dtos.response.CategoryResponseDto;
import taplink.network.menu.api.dtos.response.CategoryTemplateDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories(Long storeId);

    CategoryResponseDto getCategoryById(Long categoryId);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

    void deleteCategoryById(Long categoryId);

    void deleteImage(Long categoryId);

    List<CategoryTemplateDto> getCategoryTemplates();
}
