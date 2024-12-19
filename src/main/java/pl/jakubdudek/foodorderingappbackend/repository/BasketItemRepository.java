package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
    @Query("SELECT b FROM BasketItem b JOIN FETCH b.product JOIN FETCH b.product.variants JOIN FETCH b.product.category WHERE b.basket.user.id = :id")
    List<BasketItem> findAllByUserId(@NonNull Integer id);
}
