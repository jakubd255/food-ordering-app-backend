package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.SessionDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Session;

public class SessionMapper {
    public static SessionDto mapSessionToDto(Session session) {
        return new SessionDto(session.getId());
    }
}
