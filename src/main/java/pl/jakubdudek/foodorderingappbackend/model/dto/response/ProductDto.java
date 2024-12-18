package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import java.util.List;

public record ProductDto(
        Integer id,
        String name,
        String description,
        String image,
        CategoryDto category,
        List<VariantDto> variants
) {
}
