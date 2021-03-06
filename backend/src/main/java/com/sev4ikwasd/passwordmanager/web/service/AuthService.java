package com.sev4ikwasd.passwordmanager.web.service;

import com.sev4ikwasd.passwordmanager.web.model.LoginRequest;
import com.sev4ikwasd.passwordmanager.web.model.SignupRequest;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> login(LoginRequest request);

    Mono<Object> signup(SignupRequest request);
}
