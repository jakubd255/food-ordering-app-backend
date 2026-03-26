package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.BasketItemRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderWithoutAccountRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateOrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.*;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderStatus;
import pl.jakubdudek.foodorderingappbackend.repository.BasketItemRepository;
import pl.jakubdudek.foodorderingappbackend.repository.OrderRepository;
import pl.jakubdudek.foodorderingappbackend.repository.ProductRepository;
import pl.jakubdudek.foodorderingappbackend.util.mapper.OrderMapper;
import pl.jakubdudek.foodorderingappbackend.util.session.SessionManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final SessionManager sessionManager;

    public OrderDto placeOrderWithAccount(OrderRequest request) {
        User user = sessionManager.getUser();

        List<BasketItem> items = basketItemRepository.findAllByUserId(user.getId());

        Order order = Order.builder()
                .restaurant(Restaurant.builder().id(request.getRestaurantId()).build())
                .user(user)
                .status(OrderStatus.PENDING)
                .type(request.getType())
                .message(request.getMessage())
                .address(request.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

        items.forEach(i -> {
            i.setUser(null);
            i.setOrder(order);
        });

        order.setItems(items);

        return OrderMapper.mapOrderToDto(orderRepository.save(order));
    }

    public OrderDto placeOrderWithoutAccount(OrderWithoutAccountRequest request) {
        List<Integer> productIds = request.getItems().stream()
                .map(BasketItemRequest::getProductId)
                .distinct()
                .toList();

        Map<Integer, Product> productsById = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<BasketItem> items = request.getItems().stream().map(i -> {
            Product product = productsById.get(i.getProductId());

            if(product == null) {
                throw new RuntimeException("Product "+i.getProductId()+" not found");
            }

            return BasketItem.builder()
                    .product(product)
                    .quantity(i.getQuantity())
                    .variant(i.getVariant())
                    .build();
        }).toList();

        Order order = Order.builder()
                .restaurant(Restaurant.builder().id(request.getRestaurantId()).build())
                .status(OrderStatus.PENDING)
                .type(request.getType())
                .message(request.getMessage())
                .address(request.getAddress())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        items.forEach(i -> {
            i.setOrder(order);
        });

        order.setItems(items);

        return OrderMapper.mapOrderToDto(orderRepository.save(order));
    }

    public List<OrderDto> getAllOrders() {
        return OrderMapper.mapOrdersToDto(orderRepository.findAll());
    }

    public List<OrderDto> getOrdersByRestaurantId(Integer id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<Order> orders = orderRepository.findByRestaurantId(id, sort);
        return OrderMapper.mapOrdersToDto(orders);
    }

    public List<OrderDto> getOrdersByUserId(Integer id) {
        return OrderMapper.mapOrdersToDto(orderRepository.findByUserId(id));
    }

    public OrderDto getOrderByToken(String token) {
        return OrderMapper.mapOrderToDto(findByToken(token));
    }

    public OrderDto updateOrderById(Integer id, Integer restaurantId, UpdateOrderRequest request) {
        Order order = findOrderByIdAndRestaurantId(id, restaurantId);

        order.setStatus(request.getStatus());
        Optional.ofNullable(request.getReplyMessage()).ifPresent(order::setReplyMessage);

        return OrderMapper.mapOrderToDto(orderRepository.save(order));
    }

    private Order findByToken(String token) {
        return orderRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    private Order findOrderByIdAndRestaurantId(Integer id, Integer restaurantId) {
        return orderRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
