package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.util.validation.ValidPhone;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @ValidPhone
    private String phone;
}
