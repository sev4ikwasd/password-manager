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

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<LoginRequest> loginRequestMono = request.bodyToMono(LoginRequest.class).doOnNext(ValidationUtil::validate);
        return authService.login(loginRequestMono)
                .flatMap(token -> ServerResponse.ok().contentType(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(ToMapUtil.stringsToMap("authToken", token)));
    }

    public Mono<ServerResponse> signup(ServerRequest request) {
        Mono<SignupRequest> signupRequestMono = request.bodyToMono(SignupRequest.class).doOnNext(ValidationUtil::validate);
        return authService.signup(signupRequestMono)
                .flatMap(data -> ServerResponse.ok().contentType(json).build());
    }
}
