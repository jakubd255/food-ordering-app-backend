package pl.jakubdudek.foodorderingappbackend.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.entity.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;

public class OrderRequestValidator implements ConstraintValidator<ValidOrderRequest, OrderRequest> {
    @Override
    public boolean isValid(OrderRequest request, ConstraintValidatorContext context) {
        if(request == null) {
            return false;
        }

        //Delivery order must have address
        if(request.getType() == OrderType.DELIVERY && !isAddressValid(request.getAddress())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Address is required for DELIVERY order")
                    .addPropertyNode("address")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private boolean isAddressValid(Address address) {
        if(address == null) return false;
        if(address.getStreet() == null) return false;
        if(address.getBuilding() == null) return false;
        if(address.getFloor() == null || address.getFloor() < 0) return false;
        if(address.getCity() == null) return false;
        return address.getPostalCode() != null;
    }
}
