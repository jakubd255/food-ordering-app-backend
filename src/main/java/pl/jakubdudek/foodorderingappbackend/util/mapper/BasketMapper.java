package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.BasketItemDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;

import java.util.List;

public class BasketMapper {
    public static BasketItemDto mapBasketItemToDto(BasketItem basketItem) {
        return new BasketItemDto(
                basketItem.getId(),
                basketItem.getProduct().getId(),
                basketItem.getQuantity(),
                basketItem.getVariant()
        );
    }

    public static List<BasketItemDto> mapBasketItemsToDto(List<BasketItem> basketItems) {
        return basketItems.stream().map(BasketMapper::mapBasketItemToDto).toList();
    }
}
