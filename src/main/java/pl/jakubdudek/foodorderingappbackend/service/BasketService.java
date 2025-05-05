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
import pl.jakubdudek.foodorderingappbackend.util.mapper.BasketMapper;
import pl.jakubdudek.foodorderingappbackend.util.session.SessionManager;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final SessionManager sessionManager;

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

        basketItem.setUser(sessionManager.getUser());

        return BasketMapper.mapBasketItemToDto(basketItemRepository.save(basketItem));
    }

    public List<BasketItemDto> addItemsToBasket(List<BasketItemRequest> request) {
        return request.stream().map(this::addItemToBasket).toList();
    }

    public List<BasketItemDto> getAllBasketItems() {
        User user = sessionManager.getUser();
        return BasketMapper.mapBasketItemsToDto(basketItemRepository.findAllByUserId(user.getId()));
    }

    public BasketItemDto updateBasketItem(Integer id, BasketItemRequest request) {
        BasketItem item = findBasketItemById(id);

        if(request.getVariant() != null) {
            item.setVariant(request.getVariant());
        }
        if(request.getQuantity() != null) {
            item.setQuantity(request.getQuantity());
        }

        return BasketMapper.mapBasketItemToDto(basketItemRepository.save(item));
    }

    public void deleteItemFromBasket(Integer id) {
        BasketItem item = findBasketItemById(id);
        basketItemRepository.delete(item);
    }

    private BasketItem findBasketItemById(Integer id) {
        return basketItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));
    }
}
