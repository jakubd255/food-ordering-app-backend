package pl.jakubdudek.foodorderingappbackend.model.type;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_WORKER;

    @Override
    public String getAuthority() {
        return name();
    }
}
