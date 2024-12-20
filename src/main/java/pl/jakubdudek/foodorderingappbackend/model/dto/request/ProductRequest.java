package pl.jakubdudek.foodorderingappbackend.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull
    @NotEmpty
    private String name;

    private String description;

    @NotNull
    @PositiveOrZero
    private Integer categoryId;

    @NotNull
    @NotEmpty
    @Valid
    private List<@Valid VariantRequest> variants;
}
