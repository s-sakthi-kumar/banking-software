package com.example.gateway.controller;

import com.example.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * AuthController — the only public endpoint in the gateway.
 *
 * <p>Issues a signed JWT when presented with valid credentials.
 * This is a demo controller — credentials are hard-coded.
 *
 * <p>Learning notes:
 * <ul>
 *   <li>Spring Cloud Gateway is WebFlux-based, so controller methods return
 *       reactive types ({@link Mono}).</li>
 *   <li>In a real system, credentials would be validated against a User Service
 *       or database, and passwords would be hashed (BCrypt etc.).</li>
 * </ul>
 *
 * <pre>
 * Demo credentials
 * ─────────────────────────────────
 *  username : password  →  role
 *  user     : password  →  ROLE_USER
 *  admin    : admin     →  ROLE_ADMIN
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // Hard-coded demo users: username → [password, role]
    private static final Map<String, String[]> USERS = Map.of(
            "user",  new String[]{"password", "ROLE_USER"},
            "admin", new String[]{"admin",    "ROLE_ADMIN"}
    );

    private final JwtUtil jwtUtil;

    /**
     * Login endpoint.
     *
     * <p>Request body:  {@code {"username": "user", "password": "password"}}
     * <p>Response body: {@code {"token": "<jwt>"}}  on success
     * <p>Response:      {@code 401 Unauthorized}    on wrong credentials
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest request) {
        String[] credentials = USERS.get(request.username());

        // Check username exists and password matches
        if (credentials == null || !credentials[0].equals(request.password())) {
            log.warn("Failed login attempt for username='{}'", request.username());
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String role  = credentials[1];
        String token = jwtUtil.generateToken(request.username(), role);
        log.info("Issued JWT for username='{}' role='{}'", request.username(), role);

        return Mono.just(ResponseEntity.ok(new LoginResponse(token)));
    }

    // ── Records (Java 16+) used as simple request/response DTOs ──────────

    /**
     * Login request body — the client sends username and password as JSON.
     */
    public record LoginRequest(String username, String password) {}

    /**
     * Login response body — contains the signed JWT the client must store
     * and send as {@code Authorization: Bearer <token>} on subsequent requests.
     */
    public record LoginResponse(String token) {}
}
