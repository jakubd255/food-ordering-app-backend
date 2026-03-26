package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.model.type.UserRole;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleByEmailRequest {
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private UserRole role;
}