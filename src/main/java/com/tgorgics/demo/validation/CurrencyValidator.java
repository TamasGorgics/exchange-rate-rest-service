package com.tgorgics.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<Currency, Object> {
    @Override
    public void initialize(Currency constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String currencyCode = (String) value;
        java.util.Currency currency;
        try {
            currency = java.util.Currency.getInstance(currencyCode);
        } catch (Exception e) {
            return false;
        }
        if (!java.util.Currency.getAvailableCurrencies().contains(currency)) {
            return false;
        }

        return true;
    }
}
