package org.veedev.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.veedev.apigateway.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter jwtFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_service", r -> r.path("/profile")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("http://localhost:8081/"))
                .route("auth_service", r ->
                        r.path("/auth/login", "/auth/register")
                                .uri("http://localhost:8081/"))
                .route("account_service", r -> r
                        .path("/account/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("http://localhost:8082/"))
                .route("transaction_service", r -> r.path("/trans/**").filters(f -> f.filter(jwtFilter)).uri("http://localhost:8083/"))
                .route("report_service", r -> r.path("/report/download", "/report/transactions").filters(f -> f.filter(jwtFilter)).uri("http://localhost:8084/"))
                .build();
    }
}
