package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(DISTINCT u) FROM User u JOIN u.roles r WHERE r.role = :role")
    int countByRole(UserRole role);

    @EntityGraph(attributePaths = {"roles", "roles.restaurant"})
    Optional<User> findByEmail(@NonNull String email);

    @NonNull
    @EntityGraph(attributePaths = {"roles", "roles.restaurant"})
    Optional<User> findById(@NonNull Integer id);

    @NonNull
    @EntityGraph(attributePaths = {"roles", "roles.restaurant"})
    List<User> findAll(@NonNull Sort sort);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Integer id);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r LEFT JOIN FETCH r.restaurant WHERE r.restaurant.id = :id")
    List<User> findByRestaurantId(Integer id, Sort sort);
}
