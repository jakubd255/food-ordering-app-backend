package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemRequest {
    @NotNull
    @PositiveOrZero
    private Integer productId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    private Integer variant;
}
