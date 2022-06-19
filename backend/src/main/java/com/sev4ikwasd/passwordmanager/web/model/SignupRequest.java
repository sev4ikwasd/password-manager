package com.sev4ikwasd.passwordmanager.web.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record SignupRequest(@NotNull @NotBlank String username, @NotNull @NotBlank String password) {
}
