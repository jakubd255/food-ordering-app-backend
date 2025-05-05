package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query("SELECT s FROM Session s JOIN FETCH s.user JOIN FETCH s.user.roles WHERE s.id = :sessionId")
    Optional<Session> findSessionById(@Param("sessionId") Integer sessionId);
}
