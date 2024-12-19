package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record BasketItemDto(
        Integer id,
        Integer productId,
        Integer quantity,
        Integer variant
) {
}
