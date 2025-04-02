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
                .route("auth_service", r -> r.path("/auth/**").uri("http://localhost:8081/auth"))
                .route("account_service", r -> r.path("/account/**").uri("http://localhost:8082/account"))
                .route("transaction_service", r -> r.path("/trans/**").uri("http://localhost:8083/trans"))
                .route("report_service", r -> r.path("/report/download").uri("http://localhost:8084/report"))
                .build();
    }
}
