package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.RestaurantShortDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.RoleDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;

import java.util.List;

public class UserMapper {
    public static UserDto mapUserToDto(User user) {
        if(user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
               // user.getRoles().stream().map(Role::getRole).toList()
        );
    }

    public static UserFullDto mapUserToFullDto(User user) {
        if(user == null) {
            return null;
        }
        return new UserFullDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream().map(role -> new RoleDto(
                        role.getAuthority(),
                        role.getRestaurant() != null ? new RestaurantShortDto(
                                role.getRestaurant().getId(),
                                role.getRestaurant().getName(),
                                role.getRestaurant().getSlug()
                        ) : null
                )).toList(),
                user.getAddress()
        );
    }

    public static List<UserFullDto> mapUsersToFullDto(List<User> users) {
        return users.stream().map(UserMapper::mapUserToFullDto).toList();
    }

    public static List<UserDto> mapUsersToDto(List<User> users) {
        return users.stream().map(UserMapper::mapUserToDto).toList();
    }
}
