package pl.jakubdudek.foodorderingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubdudek.foodorderingappbackend.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
