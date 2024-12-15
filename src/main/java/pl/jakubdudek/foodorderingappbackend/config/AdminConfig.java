package pl.jakubdudek.foodorderingappbackend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.type.Role;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AdminConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    String adminPassword;

    @PostConstruct
    public void initialize() {
        if(userRepository.countByRole(Role.ROLE_ADMIN) == 0) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin has been created.");
        }
    }
}