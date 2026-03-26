package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RestaurantRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.*;
import pl.jakubdudek.foodorderingappbackend.service.RestaurantService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDto> addRestaurant(@RequestBody @Valid RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.addRestaurant(request));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/short")
    public ResponseEntity<List<RestaurantShortDto>> getAllRestaurantsShort() {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsShort());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id)")
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurantById(@PathVariable Integer id, @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.updateRestaurantById(id, request));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id)")
    @PutMapping("/{id}/photos")
    public ResponseEntity<RestaurantPhotosDto> updateRestaurantPhotosById(
            @PathVariable Integer id,
            @RequestParam(value = "deletePhotos", required = false) List<String> deletePhotos,
            @RequestParam(value = "newPhotos", required = false) List<MultipartFile> newPhotos,
            @RequestParam(value = "mainPhoto", required = false) MultipartFile mainPhoto,
            @RequestParam(value = "deleteMainPhoto", required = false) Boolean deleteMainPhoto
    ) throws IOException {
        return ResponseEntity.ok(restaurantService.updateRestaurantPhotosById(id, mainPhoto, deleteMainPhoto, newPhotos, deletePhotos));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Integer id) {
        restaurantService.deleteRestaurantById(id);
        return ResponseEntity.ok("Successfully deleted restaurant: "+id);
    }
}
