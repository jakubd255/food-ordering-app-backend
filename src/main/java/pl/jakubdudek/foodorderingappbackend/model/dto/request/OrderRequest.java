package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;
import pl.jakubdudek.foodorderingappbackend.util.validation.orderrequest.ValidOrderRequest;

@ValidOrderRequest
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull
    private Integer restaurantId;

    private String message;

    @NotNull
    private OrderType type;

    @Valid
    private Address address;
}
