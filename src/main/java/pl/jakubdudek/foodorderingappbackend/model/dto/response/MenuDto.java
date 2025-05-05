package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import java.util.List;

public record MenuDto(
        List<CategoryDto> categories,
        List<ProductDto> products,
        List<RestaurantDto> restaurants
) {
}
