package pl.jakubdudek.foodorderingappbackend.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;

@Component
@AllArgsConstructor
public class SessionExtractor {
    private final UserRepository userRepository;

    private Integer extractAccessTokenFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        return (authHeader != null && authHeader.startsWith("Bearer ")) ? Integer.valueOf(authHeader.substring(7)) : null;
    }

    private Integer extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("access-token".equals(cookie.getName())) {
                    return Integer.valueOf(cookie.getValue());
                }
            }
        }
        return null;
    }

    private Integer extractSessionId(HttpServletRequest request) {
        Integer fromHeader = extractAccessTokenFromHeader(request);
        Integer fromCookie = extractAccessTokenFromCookie(request);

        if(fromCookie != null) {
            return fromCookie;
        }
        else {
            return fromHeader;
        }
    }

    public User extractUser(HttpServletRequest request) {
        Integer sessionId = extractSessionId(request);
        if(sessionId == null) {
            return null;
        }
        return userRepository.findUserBySessionId(sessionId).orElse(null);
    }
}
