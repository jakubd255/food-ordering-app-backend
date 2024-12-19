package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.BasketItemRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.BasketItemDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.repository.BasketItemRepository;
import pl.jakubdudek.foodorderingappbackend.repository.ProductRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;
import pl.jakubdudek.foodorderingappbackend.util.SessionManager;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final SessionManager sessionManager;
    private final DtoMapper dtoMapper;

    public BasketItemDto addItemToBasket(BasketItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        BasketItem basketItem = BasketItem.builder()
                .product(product)
                .variant(request.getVariant())
                .quantity(request.getQuantity())
                .build();

        if(basketItem.getVariant() >= product.getVariants().size()) {
            throw new IllegalArgumentException("Invalid product variant");
        }

        User user = sessionManager.getUser();
        basketItem.setBasket(user.getBasket());

        return dtoMapper.mapBasketItemToDto(basketItemRepository.save(basketItem));
    }

    public List<BasketItemDto> addItemsToBasket(List<BasketItemRequest> request) {
        return request.stream().map(this::addItemToBasket).toList();
    }

    public List<BasketItemDto> getAllBasketItems() {
        User user = sessionManager.getUser();
        return basketItemRepository.findAllByUserId(user.getId()).stream().map(dtoMapper::mapBasketItemToDto).toList();
    }

    public BasketItemDto updateBasketItem(Integer id, BasketItemRequest request) {
        BasketItem item = basketItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));

        if(request.getVariant() != null) {
            item.setVariant(request.getVariant());
        }
        if(request.getQuantity() != null) {
            item.setQuantity(request.getQuantity());
        }

        return dtoMapper.mapBasketItemToDto(basketItemRepository.save(item));
    }

    public void deleteItemFromBasket(Integer id) {
        BasketItem basketItem = basketItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));

        basketItemRepository.delete(basketItem);
    }
}
