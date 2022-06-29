package com.sev4ikwasd.passwordmanager.web.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;

    public <T> void validate(T request) {
        var validationConstraints = validator.validate(request);
        if (!validationConstraints.isEmpty()) {
            throw new ConstraintViolationException(validationConstraints);
        }
    }
}
