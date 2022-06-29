package com.sev4ikwasd.passwordmanager.web.handler;

import com.sev4ikwasd.passwordmanager.web.model.LoginRequest;
import com.sev4ikwasd.passwordmanager.web.model.SignupRequest;
import com.sev4ikwasd.passwordmanager.web.service.AuthService;
import com.sev4ikwasd.passwordmanager.web.util.ToMapUtil;
import com.sev4ikwasd.passwordmanager.web.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class AuthHandler {
    private final MediaType json = MediaType.APPLICATION_JSON;

    private final AuthService authService;
    private final ValidationUtil validationUtil;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .doOnNext(validationUtil::validate)
                .flatMap(authService::login)
                .flatMap(token -> ServerResponse.ok().contentType(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(ToMapUtil.stringsToMap("authToken", token)));
    }

    public Mono<ServerResponse> signup(ServerRequest request) {
        return request.bodyToMono(SignupRequest.class)
                .doOnNext(validationUtil::validate)
                .flatMap(authService::signup)
                .flatMap(data -> ServerResponse.ok().contentType(json).build());
    }
}
