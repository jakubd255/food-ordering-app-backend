package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.ProductRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @PostMapping("/many")
    public ResponseEntity<List<ProductDto>> addProducts(@RequestBody @Valid List<ProductRequest> request) {
        return ResponseEntity.ok(productService.addProducts(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProductById(id, request));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<String> updateProductImageById(
            @PathVariable Integer id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "deleteImage", required = false) Boolean deleteImage
    ) throws IOException {
        return ResponseEntity.ok(productService.updateProductImageById(id, image, deleteImage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Successfully deleted product: "+id);
    }
}
