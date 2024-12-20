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

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        else {
            return !authentication.getName().equals("anonymousUser");
        }
    }

    public Integer getUserId() {
        User user = getUser();
        if(user != null) {
            return user.getId();
        }
        else {
            return -1;
        }
    }
}
