package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.json.WorkingHours;
import pl.jakubdudek.foodorderingappbackend.util.validation.ValidPhone;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String slug;

    @NotNull
    private Address address;

    @NotNull
    @NotEmpty
    @ValidPhone
    private String phone;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private WorkingHours workingHours;
}
