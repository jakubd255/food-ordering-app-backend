package pl.jakubdudek.foodorderingappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.exception.EmailAlreadyExistsException;
import pl.jakubdudek.foodorderingappbackend.exception.EmailNotFoundException;
import pl.jakubdudek.foodorderingappbackend.exception.InvalidPasswordException;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdatePasswordRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.LoginRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RegisterRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.repository.BasketItemRepository;
import pl.jakubdudek.foodorderingappbackend.repository.SessionRepository;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.BasketCookieExtractor;
import pl.jakubdudek.foodorderingappbackend.util.mapper.SessionMapper;
import pl.jakubdudek.foodorderingappbackend.util.mapper.UserMapper;
import pl.jakubdudek.foodorderingappbackend.util.session.SessionManager;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BasketItemRepository basketItemRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;
    private final BasketCookieExtractor basketCookieExtractor;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public SessionDto register(RegisterRequest request, String cookie) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("This email is taken");
        }

        User user = userRepository.save(
                User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build()
        );

        basketItemRepository.saveAll(basketCookieExtractor.getBasketFromCookie(cookie, user));

        Session session = Session.builder()
                .user(user)
                .build();
        return SessionMapper.mapSessionToDto(sessionRepository.save(session));
    }

    public SessionDto login(LoginRequest request, String cookie) {
        User user = findUserByEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        basketItemRepository.saveAll(basketCookieExtractor.getBasketFromCookie(cookie, user));

        Session session = sessionRepository.save(Session.builder().user(user).build());
        return SessionMapper.mapSessionToDto(session);
    }

    public UserFullDto authenticate() {
        return UserMapper.mapUserToFullDto(sessionManager.getUser());
    }

    public String updatePassword(UpdatePasswordRequest request) {
        User user = sessionManager.getUser();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Successfully updated password";
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Invalid email"));
    }
}
