package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;

public record UserDto(
        Integer id,
        String name,
        String email,
        UserRole role
) {
}
