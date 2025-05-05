package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(DISTINCT u) FROM User u JOIN u.roles r WHERE r.role = :role")
    int countByRole(UserRole role);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r LEFT JOIN FETCH r.restaurant")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r LEFT JOIN FETCH r.restaurant ORDER BY u.id ASC")
    List<User> findAll();

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Integer id);
}
