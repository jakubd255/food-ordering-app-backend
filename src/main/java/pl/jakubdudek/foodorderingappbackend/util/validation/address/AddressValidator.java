package pl.jakubdudek.foodorderingappbackend.util.validation.address;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;

public class AddressValidator implements ConstraintValidator<ValidAddress, Address> {
    @Override
    public boolean isValid(Address address, ConstraintValidatorContext context) {
        if(address == null) return false;

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if(address.getStreet() == null || address.getStreet().isBlank()) {
            context.buildConstraintViolationWithTemplate("Street is required")
                    .addPropertyNode("street").addConstraintViolation();
            valid = false;
        }

        if(address.getBuilding() == null || address.getBuilding().isBlank()) {
            context.buildConstraintViolationWithTemplate("Building is required")
                    .addPropertyNode("building").addConstraintViolation();
            valid = false;
        }

        /*if(address.getFloor() == null || address.getFloor() < 0) {
            context.buildConstraintViolationWithTemplate("Floor must be 0 or greater")
                    .addPropertyNode("floor").addConstraintViolation();
            valid = false;
        }*/

        if(address.getCity() == null || address.getCity().isBlank()) {
            context.buildConstraintViolationWithTemplate("City is required")
                    .addPropertyNode("city").addConstraintViolation();
            valid = false;
        }

        if(address.getPostalCode() == null || address.getPostalCode().isBlank()) {
            context.buildConstraintViolationWithTemplate("Postal code is required")
                    .addPropertyNode("postalCode").addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
