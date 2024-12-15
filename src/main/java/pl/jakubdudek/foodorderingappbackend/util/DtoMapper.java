package pl.jakubdudek.foodorderingappbackend.util;

import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;

@Component
public class DtoMapper {
    public UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public SessionDto mapSessionToDto(Session session) {
        return new SessionDto(session.getId());
    }
}
