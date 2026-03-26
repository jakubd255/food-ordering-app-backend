package pl.jakubdudek.foodorderingappbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserAddressUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.UserUpdateRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.UserFullDto;
import pl.jakubdudek.foodorderingappbackend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserFullDto>> getAllUsersAdmin() {
        return ResponseEntity.ok(userService.getAllUsersAdmin());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_MANAGER_'+#id)")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<UserFullDto>> getRestaurantWorkersById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getRestaurantWorkersById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserById(id, request));
    }

    @PutMapping("/{id}/address")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR #authentication.principal.id == #id")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UserAddressUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserAddressById(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Successfully deleted user: "+id);
    }
}
