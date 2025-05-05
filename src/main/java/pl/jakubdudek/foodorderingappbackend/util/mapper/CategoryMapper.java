package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.CategoryDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Category;

import java.util.List;

public class CategoryMapper {
    public static CategoryDto mapCategoryToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static List<CategoryDto> mapCategoriesToDto(List<Category> categories) {
        return categories.stream().map(CategoryMapper::mapCategoryToDto).toList();
    }

    public static Category mapRequestToCategory(String name) {
        return Category.builder().name(name).build();
    }
}
