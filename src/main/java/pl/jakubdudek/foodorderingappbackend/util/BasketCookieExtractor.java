package pl.jakubdudek.foodorderingappbackend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.BasketItemDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class BasketCookieExtractor {
    private final ObjectMapper objectMapper;

    public List<BasketItem> getBasketFromCookie(String basketCookie, User user) {
        if(basketCookie == null || basketCookie.isEmpty()) {
            return List.of();
        }

        try {
            List<BasketItemDto> basketItems = Arrays.asList(objectMapper.readValue(basketCookie, BasketItemDto[].class));
            return basketItems.stream().map(dto -> mapToBasketItem(dto, user)).toList();
        }
        catch(IOException e) {
            return List.of();
        }
    }

    private BasketItem mapToBasketItem(BasketItemDto dto, User user) {
        Product product = Product.builder().id(dto.product().id()).build();

        return BasketItem.builder()
                .user(user)
                .product(product)
                .quantity(dto.quantity())
                .variant(dto.variant())
                .build();
    }
}
