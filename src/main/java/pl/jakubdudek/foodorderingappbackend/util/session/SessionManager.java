package pl.jakubdudek.foodorderingappbackend.util.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

@Component
public class SessionManager {
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User)authentication.getPrincipal();
    }
}
