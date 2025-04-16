package org.veedev.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.veedev.apigateway.utils.JwtUtils;
import reactor.core.publisher.Mono;

@Component
public abstract class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtils jwtUtils;
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                String phoneNumber = jwtUtils.extractPhoneNumber(token);
                exchange = exchange.mutate().request(
                                exchange.getRequest().mutate()
                                        .header("X-Phone-Number", phoneNumber)
                                        .build())
                        .build();
                return chain.filter(exchange);
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
