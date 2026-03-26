package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;

public record RoleDto(
        String name,
        UserRole type,
        RestaurantShortDto restaurant
) {
}
