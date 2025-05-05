package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import java.util.List;

public record RestaurantPhotosDto(
        String mainPhoto,
        List<String> photos
) {
}
