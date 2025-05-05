package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record RestaurantShortDto(
        Integer id,
        String name,
        String slug
) {
}
