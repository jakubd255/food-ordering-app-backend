package pl.jakubdudek.foodorderingappbackend.util.validation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.google.i18n.phonenumbers.NumberParseException;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return false;
        }

        try {
            return phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(value, "PL"));
        }
        catch (NumberParseException e) {
            return false;
        }
    }
}
