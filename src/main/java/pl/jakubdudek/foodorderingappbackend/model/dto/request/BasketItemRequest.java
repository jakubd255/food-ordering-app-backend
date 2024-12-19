package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemRequest {
    private Integer productId;
    private Integer quantity;
    private Integer variant;
}
