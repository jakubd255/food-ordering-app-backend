package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
}
