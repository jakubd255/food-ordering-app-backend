package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.type.Role;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    int countByRole(Role role);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.sessions s WHERE s.id = :sessionId")
    Optional<User> findUserBySessionId(@Param("sessionId") Integer sessionId);
}
