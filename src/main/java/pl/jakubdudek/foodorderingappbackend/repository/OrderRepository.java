package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @EntityGraph(attributePaths = {"restaurant", "user", "items", "items.product", "items.product.category"})
    List<Order> findByRestaurantId(Integer id, Sort sort);

    @NonNull
    @EntityGraph(attributePaths = {"restaurant", "user", "items", "items.product", "items.product.category"})
    Optional<Order> findById(@NonNull Integer id);

    @EntityGraph(attributePaths = {"restaurant", "user", "items", "items.product", "items.product.category"})
    Optional<Order> findByToken(@NonNull String token);

    @EntityGraph(attributePaths = {"restaurant", "user", "items", "items.product", "items.product.category"})
    Optional<Order> findByIdAndRestaurantId(@NonNull Integer id, @NonNull Integer restaurantId);

    @EntityGraph(attributePaths = {"restaurant", "user", "items", "items.product", "items.product.category"})
    List<Order> findByUserId(Integer id);
}
