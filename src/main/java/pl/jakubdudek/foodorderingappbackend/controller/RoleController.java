package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateRoleByEmailRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UpdateRoleRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserFullDto> updateUserRoleAdmin(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.updateUserRoleAdmin(id));
    }

    @PutMapping("/restaurant/{restaurantId}/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#restaurantId)")
    public ResponseEntity<UserFullDto> updateUserRoleRestaurant(
            @PathVariable Integer id,
            @PathVariable Integer restaurantId,
            @RequestBody @Valid UpdateRoleRequest request
    ) {
        return ResponseEntity.ok(roleService.updateUserRoleRestaurant(id, restaurantId, request));
    }

    @PutMapping("/restaurant/{restaurantId}/email")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#restaurantId)")
    public ResponseEntity<UserFullDto> updateUserRoleRestaurantByEmail(
            @PathVariable Integer restaurantId,
            @RequestBody @Valid UpdateRoleByEmailRequest request
    ) {
        return ResponseEntity.ok(roleService.updateUserRoleRestaurantByEmail(restaurantId, request));
    }
}
