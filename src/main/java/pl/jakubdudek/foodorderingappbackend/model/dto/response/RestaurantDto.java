package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.json.WorkingHours;

import java.util.List;

public record RestaurantDto(
        Integer id,
        String name,
        String slug,
        AddressDto address,
        String phone,
        String email,
        WorkingHours workingHours,
        List<String> photos,
        String mainPhoto
) {
}
