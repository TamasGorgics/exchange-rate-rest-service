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
@Constraint(validatedBy = RateTypeValidator.class)
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface RateType {
    String message() default "Invalid exchange rate type!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
