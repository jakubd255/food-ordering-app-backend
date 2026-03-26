package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record VariantDto(
        Integer id,
        String name,
        Float price
) {
}