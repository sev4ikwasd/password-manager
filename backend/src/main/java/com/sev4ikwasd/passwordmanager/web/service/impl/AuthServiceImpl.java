package com.sev4ikwasd.passwordmanager.web.service.impl;

import com.sev4ikwasd.passwordmanager.model.exception.UserExistsError;
import com.sev4ikwasd.passwordmanager.model.user.User;
import com.sev4ikwasd.passwordmanager.model.user.UserRepository;
import com.sev4ikwasd.passwordmanager.security.JwtTokenProvider;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @Override
    public Mono<String> login(LoginRequest request) {
        String username = request.username();
        String password = request.password();
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .map(tokenProvider::createToken)
                .doOnError(error -> {
                    log.warn("User with username " + username + " was not found");
                    log.trace("User with username " + username + " was not found", error);
                });
    }

    @Override
    public Mono<Object> signup(SignupRequest request) {
        return userRepository
                .findByUsername(request.username())
                .switchIfEmpty(
                        userRepository.save(User.builder()
                                        .username(request.username())
                                        .password(passwordEncoder.encode(request.password()))
                                        .build())
                                .doOnError(error -> {
                                    log.error("Error occurred while creating user.");
                                    log.trace("Error occurred while creating user", error);
                                }))
                .flatMap(user -> {
                    log.warn("User with username " + user.getUsername() + " already exists");
                    return Mono.error(new UserExistsError(user.getUsername()));
                });
    }
}
