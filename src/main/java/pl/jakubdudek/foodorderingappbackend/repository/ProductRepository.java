package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = {"category", "variants"})
    List<Product> findProductByCategoryId(@NonNull Integer categoryId);

    @NonNull
    @EntityGraph(attributePaths = {"category", "variants"})
    Optional<Product> findById(@NonNull Integer id);

    @NonNull
    @EntityGraph(attributePaths = {"category", "variants"})
    List<Product> findAll(@NonNull Sort sort);

    @NonNull
    @EntityGraph(attributePaths = {"category", "variants"})
    List<Product> findAll();
}
