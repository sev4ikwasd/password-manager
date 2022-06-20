package com.sev4ikwasd.passwordmanager.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
public class ValidationUtil {
    private static Validator validator;

    @Autowired
    public ValidationUtil(@Qualifier("defaultValidator") Validator validator) {
        ValidationUtil.validator = validator;
    }

    public static <T> void validate(T request) {
        var validationConstraints = validator.validate(request);
        if (!validationConstraints.isEmpty()) {
            throw new ConstraintViolationException(validationConstraints);
        }
    }
}
