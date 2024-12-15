package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.type.Role;

public record UserDto(
        Integer id,
        String name,
        String email,
        Role role
) {
}
