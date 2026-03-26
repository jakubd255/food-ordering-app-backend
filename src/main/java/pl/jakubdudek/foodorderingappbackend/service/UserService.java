package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.exception.EmailAlreadyExistsException;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserAddressUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return UserMapper.mapUsersToDto(userRepository.findAll(sort));
    }

    public List<UserFullDto> getAllUsersAdmin() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return UserMapper.mapUsersToFullDto(userRepository.findAll(sort));
    }

    public UserDto getUserById(Integer id) {
        User user = findUserById(id);
        return UserMapper.mapUserToDto(user);
    }

    public List<UserFullDto> getRestaurantWorkersById(Integer id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<User> workers = userRepository.findByRestaurantId(id, sort);
        return UserMapper.mapUsersToFullDto(workers);
    }

    public UserDto updateUserById(Integer id, UserUpdateRequest request) {
        User user = findUserById(id);

        if(request.getEmail() != null) {
            if(userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new EmailAlreadyExistsException("This email is taken");
            }
            user.setEmail(request.getEmail());
        }

        Optional.ofNullable(request.getName()).ifPresent(user::setName);
        Optional.ofNullable(request.getPhone()).ifPresent(user::setPhone);

        return UserMapper.mapUserToDto(userRepository.save(user));
    }

    public UserDto updateUserAddressById(Integer id, UserAddressUpdateRequest request) {
        User user = findUserById(id);
        user.setAddress(request.getAddress());
        return UserMapper.mapUserToDto(userRepository.save(user));
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
