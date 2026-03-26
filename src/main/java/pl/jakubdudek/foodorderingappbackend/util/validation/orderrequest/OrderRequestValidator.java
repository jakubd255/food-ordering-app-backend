package pl.jakubdudek.foodorderingappbackend.util.validation.orderrequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.OrderRequest;
import pl.jakubdudek.foodorderingappbackend.model.json.Address;
import pl.jakubdudek.foodorderingappbackend.model.type.OrderType;

import java.util.Set;

public class OrderRequestValidator implements ConstraintValidator<ValidOrderRequest, OrderRequest> {
    @Autowired
    private Validator validator;

    @Override
    public boolean isValid(OrderRequest request, ConstraintValidatorContext context) {
        if (request == null) return false;

        if (request.getType() == OrderType.DELIVERY) {
            Set<ConstraintViolation<Address>> violations = validator.validate(request.getAddress());

            if (!violations.isEmpty()) {
                context.disableDefaultConstraintViolation();
                for (ConstraintViolation<Address> v : violations) {
                    context.buildConstraintViolationWithTemplate(v.getMessage())
                            .addPropertyNode("address."+v.getPropertyPath().toString())
                            .addConstraintViolation();
                }
                return false;
            }
        }

        return true;
    }
}
