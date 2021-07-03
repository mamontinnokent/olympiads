package com.olympiads.validation;

import com.olympiads.annotation.PasswordMatches;
import com.olympiads.dto.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest request = (SignupRequest) o;
        return request.getPassword().equals(request.getConfirmPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
}
