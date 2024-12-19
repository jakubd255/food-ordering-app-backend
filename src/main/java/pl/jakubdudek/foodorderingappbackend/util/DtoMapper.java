package pl.jakubdudek.foodorderingappbackend.util;

import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.*;
import pl.jakubdudek.foodorderingappbackend.model.entity.*;

@Component
public class DtoMapper {
    public UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public SessionDto mapSessionToDto(Session session) {
        return new SessionDto(session.getId());
    }

    public CategoryDto mapCategoryToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public VariantDto mapVariantToDto(Variant variant) {
        return new VariantDto(variant.getId(), variant.getName(), variant.getPrice());
    }

    public ProductDto mapProductToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                mapCategoryToDto(product.getCategory()),
                product.getVariants().stream().map(this::mapVariantToDto).toList()
        );
    }

    public BasketItemDto mapBasketItemToDto(BasketItem basketItem) {
        return new BasketItemDto(
                basketItem.getId(),
                basketItem.getProduct().getId(),
                basketItem.getQuantity(),
                basketItem.getVariant()
        );
    }
}
