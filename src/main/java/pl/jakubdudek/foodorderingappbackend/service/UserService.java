package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(dtoMapper::mapUserToDto).toList();
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return dtoMapper.mapUserToDto(user);
    }
}
