package pl.jakubdudek.foodorderingappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.LoginRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.RegisterRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.repository.SessionRepository;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;
import pl.jakubdudek.foodorderingappbackend.util.session.SessionManager;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final DtoMapper dtoMapper;
    private final SessionManager sessionManager;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public SessionDto register(RegisterRequest request) {
        User user = userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .build());

        Session session = Session.builder().user(user).build();
        return dtoMapper.mapSessionToDto(sessionRepository.save(session));
    }

    public SessionDto login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        Session session = sessionRepository.save(Session.builder().user(user).build());
        return dtoMapper.mapSessionToDto(session);
    }

    public UserDto authenticate() {
        return dtoMapper.mapUserToDto(sessionManager.getUser());
    }
}
