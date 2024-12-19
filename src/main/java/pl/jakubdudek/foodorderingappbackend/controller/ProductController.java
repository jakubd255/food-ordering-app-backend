package pl.jakubdudek.foodorderingappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.ProductRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @PostMapping("/many")
    public ResponseEntity<List<ProductDto>> addProducts(@RequestBody List<ProductRequest> request) {
        return ResponseEntity.ok(productService.addProducts(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(productService.getAllProducts(categoryId));
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
    public ResponseEntity<String> updateProductImageById(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(productService.updateProductImageById(id, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Successfully deleted product: "+id);
    }
}
