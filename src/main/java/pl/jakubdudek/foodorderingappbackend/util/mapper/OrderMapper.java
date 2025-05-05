package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Order;

import java.util.List;

public class OrderMapper {
    public static OrderDto mapOrderToDto(Order order) {
        return new OrderDto(
                order.getId(),
                UserMapper.mapUserToDto(order.getUser()),
                order.getItems().stream().map(BasketMapper::mapBasketItemToDto).toList(),
                order.getDate(),
                order.getTotal(),
                order.getMessage(),
                order.getType(),
                order.getStatus(),
                AddressMapper.mapAddressToDto(order.getAddress())
        );
    }

    public static List<OrderDto> mapOrdersToDto(List<Order> orders) {
        return orders.stream().map(OrderMapper::mapOrderToDto).toList();
    }
}
