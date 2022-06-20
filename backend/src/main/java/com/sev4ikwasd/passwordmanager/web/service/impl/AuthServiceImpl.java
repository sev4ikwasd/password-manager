package com.sev4ikwasd.passwordmanager.web.service.impl;

import com.sev4ikwasd.passwordmanager.model.user.User;
import com.sev4ikwasd.passwordmanager.model.user.UserRepository;
import com.sev4ikwasd.passwordmanager.security.JwtTokenProvider;
import com.sev4ikwasd.passwordmanager.web.exception.UserExistsError;
import com.sev4ikwasd.passwordmanager.web.model.LoginRequest;
import com.sev4ikwasd.passwordmanager.web.model.SignupRequest;
import com.sev4ikwasd.passwordmanager.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Mono<String> login(Mono<LoginRequest> request) {
        AtomicReference<String> username = new AtomicReference<>();
        return request
                .map(loginRequest ->
                {
                    username.set(loginRequest.username());
                    return new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
                })
                .flatMap(authenticationManager::authenticate)
                .map(tokenProvider::createToken)
                .doOnError(error -> {
                    log.warn("User with username " + username + " was not found");
                    log.trace("User with username " + username + " was not found", error);
                });
    }

    @Override
    public Mono<Object> signup(Mono<SignupRequest> request) {
        return request
                .flatMap(signupRequest -> userRepository.findByUsername(signupRequest.username())
                        .flatMap(user -> Mono.error(new UserExistsError(user.getUsername())))
                        .switchIfEmpty(userRepository.save(User.builder()
                                        .username(signupRequest.username())
                                        .password(passwordEncoder.encode(signupRequest.password()))
                                        .build())
                                .doOnError(error -> {
                                    log.error("Error occurred while creating user.");
                                    log.trace("Error occurred while creating user", error);
                                })));
    }
}
