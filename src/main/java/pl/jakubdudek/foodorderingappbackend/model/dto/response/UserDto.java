package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record UserDto(
        Integer id,
        String name,
        String email,
        String phone
) {
}
