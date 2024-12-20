package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;
import pl.jakubdudek.foodorderingappbackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> placeOrderWithAccount(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrderWithAccount(request));
    }

    @PostMapping("/without-account")
    public ResponseEntity<OrderDto> placeOrderWithoutAccount(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.status(501).body(orderService.placeOrderWithoutAccount(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
