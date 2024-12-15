package pl.jakubdudek.foodorderingappbackend.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {
    public void addCookie(HttpServletResponse response, Integer sessionId) {
        int expirationTime = 172800000;
        Cookie tokenCookie = createCookie("access-token", sessionId.toString(), true, expirationTime);
        Cookie isLoggedCookie = createCookie("is-logged", "true", false, expirationTime);

        response.addCookie(tokenCookie);
        response.addCookie(isLoggedCookie);
    }

    public void removeCookies(HttpServletResponse response) {
        Cookie tokenCookie = createCookie("access-token", null, true, 0);
        Cookie isLoggedCookie = createCookie("is-logged", null, false, 0);

        response.addCookie(tokenCookie);
        response.addCookie(isLoggedCookie);
    }

    private Cookie createCookie(String name, String value, boolean isHttpOnly, int maxAge) {
        Cookie  cookie = new Cookie(name, value);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }
}
