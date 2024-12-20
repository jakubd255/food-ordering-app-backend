package pl.jakubdudek.foodorderingappbackend.model.dto.response;

public record AddressDto(
        String street,
        String building,
        String apartment,
        Integer floor,
        String city,
        String postalCode
) {
}
