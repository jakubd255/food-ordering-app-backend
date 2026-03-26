package pl.jakubdudek.foodorderingappbackend.util.mapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.jakubdudek.foodorderingappbackend.model.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {
    public static GrantedAuthority mapRoleToAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getAuthority());
    }

    public static Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(RoleMapper::mapRoleToAuthority)
                .collect(Collectors.toSet());
    }
}
