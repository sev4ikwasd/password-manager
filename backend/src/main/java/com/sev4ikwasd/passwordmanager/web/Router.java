package com.sev4ikwasd.passwordmanager.web;

import com.sev4ikwasd.passwordmanager.web.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {
    private final MediaType json = MediaType.APPLICATION_JSON;

    @Bean
    public RouterFunction<ServerResponse> authEndpoint(AuthHandler handler) {
        return RouterFunctions
                .route(POST("/auth/login").and(accept(json)), handler::login)
                .andRoute(POST("/auth/signup").and(accept(json)), handler::signup);
    }
}
