package com.sev4ikwasd.passwordmanager.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistsError extends RuntimeException {
    public UserExistsError(String username) {
        super("User with username " + username + " already exists");
    }
}
