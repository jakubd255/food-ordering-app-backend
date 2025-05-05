package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.json.Address;

import java.util.List;

public record UserFullDto(
        Integer id,
        String name,
        String email,
        List<RoleDto> roles,
        Address address
) {
}
