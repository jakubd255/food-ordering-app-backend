package pl.jakubdudek.foodorderingappbackend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.entity.Role;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AdminConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    String adminPassword;

    @PostConstruct
    public void initialize() {
        if(userRepository.countByRole(UserRole.ADMIN) == 0) {
            Role role = Role.builder()
                    .role(UserRole.ADMIN)
                    .build();
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode(adminPassword))
                    .address(new Address())
                    .roles(Collections.singletonList(role))
                    .build();
            role.setUser(admin);

            userRepository.save(admin);
            System.out.println("Admin has been created.");
        }
    }
}