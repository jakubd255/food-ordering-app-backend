package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RestaurantRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.*;
import pl.jakubdudek.foodorderingappbackend.model.entity.Restaurant;
import pl.jakubdudek.foodorderingappbackend.repository.RestaurantRepository;
import pl.jakubdudek.foodorderingappbackend.util.FileManager;
import pl.jakubdudek.foodorderingappbackend.util.mapper.RestaurantMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final FileManager fileManager;

    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantDto addRestaurant(RestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .address(request.getAddress())
                .email(request.getEmail())
                .phone(request.getPhone())
                .workingHours(request.getWorkingHours())
                .build();
        return RestaurantMapper.mapRestaurantToDto(restaurantRepository.save(restaurant));
    }

    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantDto getRestaurantById(Integer id) {
        return RestaurantMapper.mapRestaurantToDto(findRestaurantById(id));
    }

    @Cacheable(value = "restaurants", key = "'full'")
    public List<RestaurantDto> getAllRestaurants() {
        return RestaurantMapper.mapRestaurantsToDto(restaurantRepository.findAll());
    }

    @Cacheable(value = "restaurants", key = "'short'")
    public List<RestaurantShortDto> getAllRestaurantsShort() {
        return RestaurantMapper.mapRestaurantsToShortDto(restaurantRepository.findAll());
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantDto updateRestaurantById(Integer id, RestaurantRequest request) {
        Restaurant restaurant = findRestaurantById(id);

        Optional.ofNullable(request.getName()).ifPresent(restaurant::setName);
        Optional.ofNullable(request.getSlug()).ifPresent(restaurant::setSlug);
        Optional.ofNullable(request.getAddress()).ifPresent(restaurant::setAddress);
        Optional.ofNullable(request.getEmail()).ifPresent(restaurant::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(restaurant::setPhone);
        Optional.ofNullable(request.getWorkingHours()).ifPresent(restaurant::setWorkingHours);

        return RestaurantMapper.mapRestaurantToDto(restaurantRepository.save(restaurant));
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantPhotosDto updateRestaurantPhotosById(
            Integer id,
            MultipartFile mainPhoto,
            Boolean deleteMainPhoto,
            List<MultipartFile> newPhotos,
            List<String> deletePhotos
    ) throws IOException {
        Restaurant restaurant = findRestaurantById(id);

        String mainPhotoName = null;

        //Delete main photo
        if(Boolean.TRUE.equals(deleteMainPhoto) && restaurant.getMainPhoto() != null && !restaurant.getMainPhoto().isEmpty()) {
            fileManager.deleteFile(restaurant.getMainPhoto());
            restaurant.setMainPhoto(null);
        }
        //Add or update main photo
        else if(mainPhoto != null && !mainPhoto.isEmpty()) {
            mainPhotoName = fileManager.uploadFile(mainPhoto);
            //Delete old photo
            if(restaurant.getMainPhoto() != null && !restaurant.getMainPhoto().isEmpty()) {
                fileManager.deleteFile(restaurant.getMainPhoto());
            }
            restaurant.setMainPhoto(mainPhotoName);
        }

        //Get all photos
        List<String> photos = new ArrayList<>(Optional.ofNullable(restaurant.getPhotos()).orElse(List.of()));

        //Delete selected ones
        if(deletePhotos != null && !deletePhotos.isEmpty()) {
            photos.removeAll(deletePhotos);
            deletePhotos.forEach(p -> {
                try {
                    fileManager.deleteFile(p);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        //Add new photos
        if(newPhotos != null) {
            List<String> newPhotoUrls = newPhotos.stream()
                    .filter(photo -> !photo.isEmpty())
                    .map(fileManager::uploadFile)
                    .toList();
            photos.addAll(newPhotoUrls);
        }

        restaurant.setPhotos(photos);
        restaurantRepository.save(restaurant);

        return new RestaurantPhotosDto(mainPhotoName, photos);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void deleteRestaurantById(Integer id) {
        Restaurant restaurant = findRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    private Restaurant findRestaurantById(Integer id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not fount"));
    }
}
