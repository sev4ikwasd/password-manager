package com.sev4ikwasd.passwordmanager.web.handler;

import com.sev4ikwasd.passwordmanager.model.exception.UserExistsError;
import com.sev4ikwasd.passwordmanager.web.model.LoginRequest;
import com.sev4ikwasd.passwordmanager.web.model.SignupRequest;
import com.sev4ikwasd.passwordmanager.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.sev4ikwasd.passwordmanager.web.util.ErrorsToMap.errorsToMap;

@Component
@RequiredArgsConstructor
public class AuthHandler {
    private final MediaType json = MediaType.APPLICATION_JSON;

    private final AuthService authService;
    private final Validator validator;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, LoginRequest.class.getName());
                    validator.validate(body, errors);

                    if (errors.getAllErrors().isEmpty()) {
                        return authService.login(body)
                                .flatMap(token -> {
                                    Map<String, String> tokenMap = new HashMap<>();
                                    tokenMap.put("authToken", token);

                                    return ServerResponse.ok().contentType(json)
                                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                            .bodyValue(tokenMap);
                                })
                                .onErrorResume(error -> ServerResponse.badRequest().build());
                    } else {
                        return ServerResponse.badRequest().bodyValue(errorsToMap(errors));
                    }
                });
    }

    public Mono<ServerResponse> signup(ServerRequest request) {
        return request.bodyToMono(SignupRequest.class)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, SignupRequest.class.getName());
                    validator.validate(body, errors);

                    if (errors.getAllErrors().isEmpty()) {
                        return authService.signup(body)
                                .flatMap(data -> ServerResponse.ok().contentType(json).build())
                                .onErrorResume(error -> {
                                    if (error instanceof UserExistsError)
                                        return ServerResponse.badRequest().bodyValue("User already exists");
                                    else
                                        return ServerResponse.badRequest().build();
                                });

                    } else {
                        return ServerResponse.badRequest().bodyValue(errorsToMap(errors));
                    }
                });
    }
}
