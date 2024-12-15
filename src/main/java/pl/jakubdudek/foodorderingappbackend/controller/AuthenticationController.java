package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.LoginRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RegisterRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.service.AuthenticationService;
import pl.jakubdudek.foodorderingappbackend.util.CookieManager;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CookieManager cookieManager;

    @PostMapping("/register")
    public ResponseEntity<SessionDto> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        SessionDto session = authenticationService.register(request);
        cookieManager.addCookie(response, session.id());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/log-in")
    public ResponseEntity<SessionDto> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        SessionDto session = authenticationService.login(request);
        cookieManager.addCookie(response, session.id());
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<UserDto> authenticate() {
        return ResponseEntity.ok(authenticationService.authenticate());
    }
}
