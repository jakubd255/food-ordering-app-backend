package pl.jakubdudek.foodorderingappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.CategoryDto;
import pl.jakubdudek.foodorderingappbackend.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addProduct(@RequestBody String request) {
        return ResponseEntity.ok(categoryService.addCategory(request));
    }

    @PostMapping("/many")
    public ResponseEntity<List<CategoryDto>> addProducts(@RequestBody List<String> request) {
        return ResponseEntity.ok(categoryService.addCategories(request));
    }
}
