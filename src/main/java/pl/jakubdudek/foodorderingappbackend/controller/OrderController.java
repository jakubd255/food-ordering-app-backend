package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderWithoutAccountRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateOrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;
import pl.jakubdudek.foodorderingappbackend.service.OrderNotificationService;
import pl.jakubdudek.foodorderingappbackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderNotificationService orderNotificationService;

    @PostMapping
    public ResponseEntity<OrderDto> placeOrderWithAccount(@RequestBody @Valid OrderRequest request) {
        OrderDto order = orderService.placeOrderWithAccount(request);
        orderNotificationService.notifyCustomer(order);
        orderNotificationService.notifyRestaurantStaffNew(order);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/without-account")
    public ResponseEntity<OrderDto> placeOrderWithoutAccount(@RequestBody @Valid OrderWithoutAccountRequest request) {
        OrderDto order = orderService.placeOrderWithoutAccount(request);
        orderNotificationService.notifyCustomer(order);
        orderNotificationService.notifyRestaurantStaffNew(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{token}")
    public ResponseEntity<OrderDto> getOrderByToken(@PathVariable String token) {
        return ResponseEntity.ok(orderService.getOrderByToken(token));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id) OR hasAuthority('ROLE_WORKER_'+#id)")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<OrderDto>> getRestaurantOrdersById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurantId(id));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR #authentication.principal.id == #id")
    public ResponseEntity<List<OrderDto>> getUserOrdersById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id) OR hasAnyAuthority('ROLE_WORKER_'+#id)")
    @PutMapping("/restaurant/{id}/order/{orderId}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable Integer id, @PathVariable Integer orderId, @RequestBody UpdateOrderRequest request) {
        OrderDto order = orderService.updateOrderById(orderId, id, request);
        orderNotificationService.notifyRestaurantStaff(order);
        orderNotificationService.notifyCustomer(order);
        return ResponseEntity.ok(order);
    }
}
