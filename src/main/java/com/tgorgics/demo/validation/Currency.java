package com.tgorgics.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface Currency {
    String message() default "Invalid currency code!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
