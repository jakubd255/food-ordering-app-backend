package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.exception.EmailAlreadyExistsException;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateRoleRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserAddressUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Restaurant;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.repository.RestaurantRepository;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.RoleManager;
import pl.jakubdudek.foodorderingappbackend.util.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public List<UserDto> getAllUsers() {
        return UserMapper.mapUsersToDto(userRepository.findAll());
    }

    public List<UserFullDto> getAllUsersAdmin() {
        return UserMapper.mapUsersToFullDto(userRepository.findAll());
    }

    public UserDto getUserById(Integer id) {
        User user = findUserById(id);
        return UserMapper.mapUserToDto(user);
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

    @Transactional
    public UserFullDto updateUserRoleAdmin(Integer id) {
        User user = findUserById(id);
        RoleManager.toggleAdmin(user);
        return UserMapper.mapUserToFullDto(userRepository.save(user));
    }

    @Transactional
    public UserFullDto updateUserRoleRestaurant(Integer id, UpdateRoleRequest request) {
        User user = findUserById(id);

        UserRole role = request.getRole();
        Integer restaurantId = request.getRestaurantId();
        boolean isRestaurantRole = role == UserRole.MANAGER || role == UserRole.WORKER;

        //Update role related to the restaurant
        if(isRestaurantRole && restaurantId != null) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
            RoleManager.toggleRestaurantRole(user, role, restaurant);
        }

        return UserMapper.mapUserToFullDto(userRepository.save(user));
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
