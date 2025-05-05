package pl.jakubdudek.foodorderingappbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.CategoryDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.MenuDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.RestaurantDto;
import pl.jakubdudek.foodorderingappbackend.service.CategoryService;
import pl.jakubdudek.foodorderingappbackend.service.ProductService;
import pl.jakubdudek.foodorderingappbackend.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<MenuDto> getMenu() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(new MenuDto(categories, products, restaurants));
    }
}
