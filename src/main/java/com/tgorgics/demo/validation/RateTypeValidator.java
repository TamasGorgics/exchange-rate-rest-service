package com.tgorgics.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RateTypeValidator implements ConstraintValidator<RateType, Object> {
    @Override
    public void initialize(RateType constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            com.tgorgics.demo.enums.RateType.valueOf((String) value);
        } catch (Exception e) {
            return false;
        }

        if (Arrays.asList(com.tgorgics.demo.enums.RateType.values()).contains(com.tgorgics.demo.enums.RateType.valueOf((String) value))) {
            return true;
        }

        return false;
    }
}
