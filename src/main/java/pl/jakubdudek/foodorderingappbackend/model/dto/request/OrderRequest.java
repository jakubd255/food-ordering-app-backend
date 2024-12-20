package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jakubdudek.foodorderingappbackend.model.entity.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;
import pl.jakubdudek.foodorderingappbackend.util.validation.ValidOrderRequest;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidOrderRequest
public class OrderRequest {
    private String message;

    @NotNull
    private OrderType type;

    @Valid
    private List<@Valid BasketItemRequest> items;

    private Address address;
}
