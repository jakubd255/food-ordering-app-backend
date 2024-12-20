package pl.jakubdudek.foodorderingappbackend.util;

import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.*;
import pl.jakubdudek.foodorderingappbackend.model.entity.*;

@Component
public class DtoMapper {
    public UserDto mapUserToDto(User user) {
        if(user == null) {
            return null;
        }
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

    public AddressDto mapAddressToDto(Address address) {
        if(address == null) {
            return null;
        }
        return new AddressDto(
                address.getStreet(),
                address.getBuilding(),
                address.getApartment(),
                address.getFloor(),
                address.getCity(),
                address.getPostalCode()
        );
    }

    public OrderDto mapOrderToDto(Order order) {
        return new OrderDto(
                order.getId(),
                mapUserToDto(order.getUser()),
                order.getItems().stream().map(this::mapBasketItemToDto).toList(),
                order.getDate(),
                order.getTotal(),
                order.getMessage(),
                order.getType(),
                order.getStatus(),
                mapAddressToDto(order.getAddress())
        );
    }
}
