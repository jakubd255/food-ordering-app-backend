package pl.jakubdudek.foodorderingappbackend.model.dto.response;

import pl.jakubdudek.foodorderingappbackend.model.type.OrderStatus;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;

import java.util.Date;
import java.util.List;

public record OrderDto(
        Integer id,
        String token,
        UserDto user,
        List<BasketItemDto> items,
        Date date,
        Float total,
        String message,
        String replyMessage,
        OrderType type,
        OrderStatus status,
        AddressDto address,
        Integer restaurantId
) {
}
