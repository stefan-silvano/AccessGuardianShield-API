package com.cybersecurity.iam.payload.validator;

import com.cybersecurity.iam.exception.type.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class RequestValidator {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty())
            for (ConstraintViolation<T> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
    }

    public static <T> String isObjectValid(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty())
            for (ConstraintViolation<T> violation : violations) {
                return violation.getMessage();
            }

        return null;
    }
}