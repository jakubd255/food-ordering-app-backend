package pl.jakubdudek.foodorderingappbackend.model.json;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String building;
    private String apartment;
    private Integer floor;
    private String city;
    private String postalCode;
}
