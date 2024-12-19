package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.variants WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@NonNull Integer categoryId);

    @NonNull
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.variants WHERE p.id = :id")
    Optional<Product> findById(@NonNull Integer id);

    @NonNull
    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.variants")
    List<Product> findAll();
}
