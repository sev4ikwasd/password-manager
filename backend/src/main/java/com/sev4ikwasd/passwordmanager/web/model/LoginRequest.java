package com.sev4ikwasd.passwordmanager.web.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record LoginRequest(@NotNull @NotBlank String username, @NotNull @NotBlank String password) {
}
