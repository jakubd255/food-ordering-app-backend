package pl.jakubdudek.foodorderingappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.BasketItemRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.BasketItemDto;
import pl.jakubdudek.foodorderingappbackend.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@AllArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping
    public ResponseEntity<BasketItemDto> addItemToBasket(@RequestBody BasketItemRequest request) {
        return ResponseEntity.ok(basketService.addItemToBasket(request));
    }

    @PostMapping("/many")
    public ResponseEntity<List<BasketItemDto>> addItemsToBasket(@RequestBody List<BasketItemRequest> request) {
        return ResponseEntity.ok(basketService.addItemsToBasket(request));
    }

    @GetMapping
    public ResponseEntity<List<BasketItemDto>> getBasket() {
        return ResponseEntity.ok(basketService.getAllBasketItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasketItemDto> updateBasketItem(@PathVariable Integer id, @RequestBody BasketItemRequest request) {
        return ResponseEntity.ok(basketService.updateBasketItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemFromBasket(@PathVariable Integer id) {
        basketService.deleteItemFromBasket(id);
        return ResponseEntity.ok("Successfully deleted item: "+id);
    }
}
