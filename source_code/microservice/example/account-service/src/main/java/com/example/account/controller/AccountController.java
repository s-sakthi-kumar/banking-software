package com.example.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AccountController — a learning stub that proves the gateway correctly
 * validates JWT tokens and injects user identity headers before forwarding.
 *
 * <p>Security note: this service has NO security configuration of its own.
 * All authentication and authorization happen at the gateway. In production
 * you would also verify the forwarded headers (or use mTLS between gateway
 * and services) to prevent clients from bypassing the gateway.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>{@code GET /accounts/me}   — accessible with any valid JWT</li>
 *   <li>{@code GET /admin/info}    — accessible only with ROLE_ADMIN JWT
 *       (enforced by the gateway's JwtAuthFilter)</li>
 * </ul>
 */
@RestController
public class AccountController {

    /**
     * Returns the identity the gateway forwarded after validating the JWT.
     *
     * <p>When you call this via the gateway with a valid token you will see:
     * <pre>
     * {
     *   "service"  : "account-service",
     *   "endpoint" : "/accounts/me",
     *   "username" : "user",
     *   "role"     : "ROLE_USER",
     *   "message"  : "Gateway auth worked! Your identity was forwarded."
     * }
     * </pre>
     */
    @GetMapping("/accounts/me")
    public ResponseEntity<Map<String, String>> me(
            @RequestHeader(value = "X-User-Name", defaultValue = "unknown") String username,
            @RequestHeader(value = "X-User-Role", defaultValue = "unknown") String role) {

        return ResponseEntity.ok(Map.of(
                "service",   "account-service",
                "endpoint",  "/accounts/me",
                "username",  username,
                "role",      role,
                "message",   "Gateway auth worked! Your identity was forwarded."
        ));
    }

    /**
     * Admin-only endpoint — the gateway blocks non-admin requests before
     * they ever reach this method.
     *
     * <p>If you hit this endpoint directly (bypassing the gateway) with any
     * headers, it will respond — demonstrating why network-level isolation
     * between gateway and services matters in production.
     */
    @GetMapping("/admin/info")
    public ResponseEntity<Map<String, String>> adminInfo(
            @RequestHeader(value = "X-User-Name", defaultValue = "unknown") String username,
            @RequestHeader(value = "X-User-Role", defaultValue = "unknown") String role) {

        return ResponseEntity.ok(Map.of(
                "service",   "account-service",
                "endpoint",  "/admin/info",
                "username",  username,
                "role",      role,
                "message",   "You have ADMIN access. Gateway RBAC is working!"
        ));
    }
}
