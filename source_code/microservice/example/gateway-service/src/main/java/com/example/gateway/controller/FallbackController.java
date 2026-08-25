package com.example.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * FallbackController — returns a friendly error when a downstream service
 * is unavailable (used as a circuit-breaker fallback target later).
 *
 * <p>Learning notes:
 * <ul>
 *   <li>This endpoint is mapped as the {@code fallbackUri} in a CircuitBreaker
 *       filter on a route — when the downstream service fails N times the
 *       gateway redirects here instead of returning a raw 500.</li>
 *   <li>It is reachable directly at {@code GET /fallback} but is normally only
 *       invoked by the gateway internally.</li>
 * </ul>
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<ResponseEntity<FallbackResponse>> fallback() {
        FallbackResponse body = new FallbackResponse(
                "SERVICE_UNAVAILABLE",
                "The requested service is temporarily unavailable. Please try again later."
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
    }

    public record FallbackResponse(String error, String message) {}
}
