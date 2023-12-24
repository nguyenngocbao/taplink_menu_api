package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.request.CategoryRequestDto;
import taplink.network.menu.api.dtos.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(Long storeId, CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories(Long storeId);

    CategoryResponseDto getCategoryById(Long categoryId);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

    void deleteCategoryById(Long categoryId);
}
