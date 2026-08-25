package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RouteConfig — programmatic route configuration hook.
 *
 * <p>Routes are primarily declared in {@code application.yml} for readability.
 * This class is kept as a minimal placeholder that you can extend later to add
 * programmatic filters (e.g. circuit breakers, request transformations) to
 * individual routes without touching YAML.
 *
 * <p>Learning notes:
 * <ul>
 *   <li>Routes defined here and in YAML are merged at startup — no conflict.</li>
 *   <li>To add a per-route filter in code: use
 *       {@code .filters(f -> f.circuitBreaker(...))} inside the route builder.</li>
 * </ul>
 */
@Configuration
public class RouteConfig {

    /**
     * Example of how you COULD define a route programmatically.
     * Currently a no-op — remove the comment block when you want to experiment.
     *
     * <pre>{@code
     * @Bean
     * public RouteLocator customRoutes(RouteLocatorBuilder builder) {
     *     return builder.routes()
     *         .route("example-route", r -> r
     *             .path("/example/**")
     *             .filters(f -> f.addRequestHeader("X-Source", "gateway"))
     *             .uri("http://localhost:9090"))
     *         .build();
     * }
     * }</pre>
     */
    // Intentionally empty — routes live in application.yml for this demo.
    // Add @Bean RouteLocator methods here when you need programmatic routes.
}
