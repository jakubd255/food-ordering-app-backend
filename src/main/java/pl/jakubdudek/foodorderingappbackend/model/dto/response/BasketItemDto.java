package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record BasketItemDto(
        Integer id,
        ProductDto product,
        Integer quantity,
        Integer variant
) {
}
