package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressUpdateRequest {
    @NotNull
    private Address address;
}
