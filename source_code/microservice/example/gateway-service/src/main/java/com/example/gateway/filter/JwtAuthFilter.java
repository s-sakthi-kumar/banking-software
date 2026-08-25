package com.example.gateway.filter;

import com.example.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JwtAuthFilter — a {@link GlobalFilter} that runs on EVERY request entering the gateway.
 *
 * <p>Request lifecycle (learning notes):
 * <pre>
 *   Inbound request
 *       │
 *       ▼
 *   Is path /auth/** ?  ──YES──▶  skip filter, forward as-is
 *       │ NO
 *       ▼
 *   Has Authorization: Bearer <token> header?  ──NO──▶  401
 *       │ YES
 *       ▼
 *   Is token valid?  ──NO (JwtException)──▶  401
 *       │ YES
 *       ▼
 *   Is path /admin/** and role != ROLE_ADMIN?  ──YES──▶  403
 *       │ NO
 *       ▼
 *   Mutate request: add X-User-Name + X-User-Role headers
 *       │
 *       ▼
 *   Forward to downstream microservice
 * </pre>
 *
 * <p>Order {@code -1} ensures this filter runs before any route-specific filters.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final String AUTH_HEADER  = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ADMIN_ROLE   = "ROLE_ADMIN";

    private final JwtUtil jwtUtil;

    @Override
    public int getOrder() {
        // Negative value = runs before built-in gateway filters
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.debug("JwtAuthFilter processing path: {}", path);

        // ── Step 1: Skip public auth endpoints ────────────────────────────
        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        // ── Step 2: Require Authorization header ──────────────────────────
        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.warn("Missing or malformed Authorization header for path: {}", path);
            return respond(exchange, HttpStatus.UNAUTHORIZED);
        }

        // ── Step 3: Validate JWT ───────────────────────────────────────────
        String token = authHeader.substring(BEARER_PREFIX.length());
        Claims claims;
        try {
            claims = jwtUtil.validateToken(token);
        } catch (JwtException ex) {
            log.warn("Invalid JWT on path {}: {}", path, ex.getMessage());
            return respond(exchange, HttpStatus.UNAUTHORIZED);
        }

        String username = jwtUtil.extractUsername(claims);
        String role     = jwtUtil.extractRole(claims);
        log.debug("Authenticated user='{}' role='{}' accessing '{}'", username, role, path);

        // ── Step 4: Role-based check for /admin/** routes ─────────────────
        if (path.startsWith("/admin/") && !ADMIN_ROLE.equals(role)) {
            log.warn("Access denied: user='{}' role='{}' attempted admin path '{}'",
                    username, role, path);
            return respond(exchange, HttpStatus.FORBIDDEN);
        }

        // ── Step 5: Forward request with user identity headers ────────────
        // Downstream services receive X-User-Name and X-User-Role instead of
        // the raw JWT — they never need to parse tokens themselves.
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-User-Name", username)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * Terminate the exchange immediately with the given HTTP status.
     * No response body is written — just the status code.
     */
    private Mono<Void> respond(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }
}
