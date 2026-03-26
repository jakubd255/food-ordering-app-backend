package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @EntityGraph(attributePaths = {"user", "user.roles", "user.roles.restaurant"})
    Optional<Session> findSessionById(Integer id);
}
