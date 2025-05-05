package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.LoginRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RegisterRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdatePasswordRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.service.AuthenticationService;
import pl.jakubdudek.foodorderingappbackend.util.session.CookieManager;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CookieManager cookieManager;

    @PostMapping("/register")
    public ResponseEntity<SessionDto> register(@RequestBody @Valid RegisterRequest request, HttpServletResponse response) {
        SessionDto session = authenticationService.register(request);
        cookieManager.addCookie(response, session.id());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/log-in")
    public ResponseEntity<SessionDto> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        SessionDto session = authenticationService.login(request);
        cookieManager.addCookie(response, session.id());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/log-out")
    public ResponseEntity<String> logOut(HttpServletResponse response) {
        cookieManager.removeCookies(response);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping
    public ResponseEntity<UserFullDto> authenticate() {
        return ResponseEntity.ok(authenticationService.authenticate());
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
        return ResponseEntity.ok(authenticationService.updatePassword(request));
    }
}
