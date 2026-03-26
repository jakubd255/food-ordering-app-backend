package pl.jakubdudek.foodorderingappbackend.util.validation.orderrequest;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderRequestValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderRequest {
    String message() default "Invalid order request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
