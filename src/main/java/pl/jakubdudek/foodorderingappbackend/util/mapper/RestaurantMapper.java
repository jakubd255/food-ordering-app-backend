package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.RestaurantDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.RestaurantShortDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Restaurant;

import java.util.List;

public class RestaurantMapper {
    public static RestaurantDto mapRestaurantToDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getSlug(),
                AddressMapper.mapAddressToDto(restaurant.getAddress()),
                restaurant.getPhone(),
                restaurant.getEmail(),
                restaurant.getWorkingHours(),
                restaurant.getPhotos(),
                restaurant.getMainPhoto()
        );
    }

    public static List<RestaurantDto> mapRestaurantsToDto(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantMapper::mapRestaurantToDto).toList();
    }

    public static RestaurantShortDto mapRestaurantToShortDto(Restaurant restaurant) {
        return new RestaurantShortDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getSlug()
        );
    }

    public static List<RestaurantShortDto> mapRestaurantsToShortDto(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantMapper::mapRestaurantToShortDto).toList();
    }
}
