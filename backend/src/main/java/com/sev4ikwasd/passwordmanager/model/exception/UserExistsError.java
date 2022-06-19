package com.sev4ikwasd.passwordmanager.model.exception;

public class UserExistsError extends RuntimeException {
    public UserExistsError(String username) {
        super("User with username " + username + " already exists");
    }
}
