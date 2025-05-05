package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record RoleDto(
        String name,
        RestaurantShortDto restaurant
) {
}
