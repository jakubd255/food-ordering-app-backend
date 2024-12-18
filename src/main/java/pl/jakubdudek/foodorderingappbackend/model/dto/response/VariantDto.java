package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import jakarta.persistence.Column;

public record VariantDto(
        Integer id,
        String name,
        Float price
) {
}