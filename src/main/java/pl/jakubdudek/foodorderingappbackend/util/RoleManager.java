package pl.jakubdudek.foodorderingappbackend.util;

import pl.jakubdudek.foodorderingappbackend.model.entity.Restaurant;
import pl.jakubdudek.foodorderingappbackend.model.entity.Role;
import pl.jakubdudek.foodorderingappbackend.model.entity.User;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;

import java.util.Optional;

public class RoleManager {
    public static void toggleAdmin(User user) {
        UserRole role = UserRole.ADMIN;

        boolean removed = user.getRoles().removeIf(r -> r.getRole() == UserRole.ADMIN && r.getRestaurant() == null);
        if(!removed) {
            user.getRoles().add(new Role(null, user, UserRole.ADMIN, null));
        }
    }

    public static void toggleRestaurantRole(User user, UserRole role, Restaurant restaurant) {
        Optional<Role> existing = user.getRoles().stream()
                .filter(r -> r.getRestaurant() != null && r.getRestaurant().getId().equals(restaurant.getId()))
                .findFirst();

        if(existing.isPresent()) {
            Role existingRole = existing.get();
            if(existingRole.getRole() == role) {
                //If the same role and restaurant - delete
                user.getRoles().remove(existingRole);
            }
            else {
                //If another role in the same restaurant - update
                user.getRoles().remove(existingRole);
                user.getRoles().add(new Role(null, user, role, restaurant));
            }
        }
        else {
            //Add new role for restaurant
            user.getRoles().add(new Role(null, user, role, restaurant));
        }
    }
}
