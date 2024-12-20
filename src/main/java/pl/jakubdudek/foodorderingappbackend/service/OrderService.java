package pl.jakubdudek.foodorderingappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;
import pl.jakubdudek.foodorderingappbackend.model.entity.Order;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderStatus;
import pl.jakubdudek.foodorderingappbackend.repository.BasketItemRepository;
import pl.jakubdudek.foodorderingappbackend.repository.OrderRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;
import pl.jakubdudek.foodorderingappbackend.util.session.SessionManager;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketItemRepository basketItemRepository;
    private final DtoMapper dtoMapper;
    private final SessionManager sessionManager;

    public OrderDto placeOrderWithAccount(OrderRequest request) {
        User user = sessionManager.getUser();

        List<BasketItem> items = basketItemRepository.findAllByUserId(user.getId());

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .type(request.getType())
                .message(request.getMessage())
                .address(request.getAddress())
                .build();

        items.forEach(i -> {
            i.setBasket(null);
            i.setOrder(order);
        });

        order.setItems(items);

        return dtoMapper.mapOrderToDto(orderRepository.save(order));
    }

    public OrderDto placeOrderWithoutAccount(OrderRequest request) {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .type(request.getType())
                .message(request.getMessage())
                .address(request.getAddress())
                .build();

        return dtoMapper.mapOrderToDto(orderRepository.save(order));
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(dtoMapper::mapOrderToDto).toList();
    }
}
