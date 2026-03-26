package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateRoleByEmailRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateRoleRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Restaurant;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;
import pl.jakubdudek.foodorderingappbackend.repository.RestaurantRepository;
import pl.jakubdudek.foodorderingappbackend.repository.UserRepository;
import pl.jakubdudek.foodorderingappbackend.util.RoleManager;
import pl.jakubdudek.foodorderingappbackend.util.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public UserFullDto updateUserRoleAdmin(Integer id) {
        User user = findUserById(id);
        RoleManager.toggleAdmin(user);
        return UserMapper.mapUserToFullDto(userRepository.save(user));
    }

    @Transactional
    public UserFullDto updateUserRoleRestaurant(Integer id, Integer restaurantId, UpdateRoleRequest request) {
        User user = findUserById(id);

        UserRole role = request.getRole();
        boolean isRestaurantRole = role == UserRole.MANAGER || role == UserRole.WORKER;

        //Update role related to the restaurant
        if(isRestaurantRole) {
            Restaurant restaurant = findRestaurantById(restaurantId);
            RoleManager.toggleRestaurantRole(user, role, restaurant);
        }

        return UserMapper.mapUserToFullDto(userRepository.save(user));
    }

    @Transactional
    public UserFullDto updateUserRoleRestaurantByEmail(Integer restaurantId, UpdateRoleByEmailRequest request) {
        User user = findUserByEmail(request.getEmail());

        UserRole role = request.getRole();
        boolean isRestaurantRole = role == UserRole.MANAGER || role == UserRole.WORKER;

        //Update role related to the restaurant
        if(isRestaurantRole) {
            Restaurant restaurant = findRestaurantById(restaurantId);
            RoleManager.toggleRestaurantRole(user, role, restaurant);
        }

        return UserMapper.mapUserToFullDto(userRepository.save(user));
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private Restaurant findRestaurantById(Integer id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }
}
