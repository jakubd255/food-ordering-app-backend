package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String newPassword;

    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String currentPassword;
}
