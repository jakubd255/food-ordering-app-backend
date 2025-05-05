package pl.jakubdudek.foodorderingappbackend.model.dto.request;

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
public class UpdateRoleRequest {
    @NotNull
    private UserRole role;
    private Integer restaurantId;
}
