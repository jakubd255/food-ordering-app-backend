package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.response.AddressDto;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;

public class AddressMapper {
    public static AddressDto mapAddressToDto(Address address) {
        if(address == null) {
            return null;
        }
        return new AddressDto(
                address.getStreet(),
                address.getBuilding(),
                address.getApartment(),
                address.getFloor(),
                address.getCity(),
                address.getPostalCode()
        );
    }
}
