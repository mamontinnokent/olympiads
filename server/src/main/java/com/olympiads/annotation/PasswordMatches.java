package com.olympiads.annotation;

import com.olympiads.validation.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Invalid Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
