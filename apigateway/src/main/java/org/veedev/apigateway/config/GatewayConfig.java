package org.veedev.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_service", r -> r.path("/auth/**", "/profile").uri("http://localhost:8081/"))
                .route("account_service", r -> r.path("/account/**").uri("http://localhost:8082/"))
                .route("transaction_service", r -> r.path("/trans/**").uri("http://localhost:8083/"))
                .route("report_service", r -> r.path("/report/download").uri("http://localhost:8084/"))
                .build();
    }
}
