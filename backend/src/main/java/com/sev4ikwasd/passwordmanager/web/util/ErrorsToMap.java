package com.sev4ikwasd.passwordmanager.web.util;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorsToMap {
    public static Map<String, String> errorsToMap(Errors errors) {
        Map<String, String> map = new HashMap<>();
        errors.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            map.put(fieldName, errorMessage);
        });
        return map;
    }
}
