# Auth at the Edge — Gateway Service Plan

## Overview

Build a `gateway-service` using **Spring Cloud Gateway** that enforces JWT-based
Authentication and Authorization **before** requests reach any downstream microservice.
A companion `account-service` stub is included so the full request flow can be
observed end-to-end.

The project is intentionally simple and learning-focused:
- Hard-coded demo credentials for token issuance
- A single global JWT filter
- Role claims forwarded as headers to downstream services

---

## Architecture

```
Client
  │
  ▼
gateway-service  (port 8080)
  ├── POST /auth/login          ← public, issues JWT
  ├── GET  /accounts/**         ← protected, any valid JWT
  └── GlobalFilter: JwtAuthFilter
        └── validates token → strips auth header → adds X-User-Name, X-User-Role
  │
  ▼
account-service  (port 8081)   ← stub, reads forwarded headers
```

---

## Sub-Tasks

---

### Sub-Task 1 — Scaffold `gateway-service` Maven project

**Intent**
Create the Maven project skeleton with all required dependencies so the service
compiles before any logic is added.

**Expected Outcomes**
- `gateway-service/pom.xml` exists with Spring Boot 3.x, Spring Cloud Gateway,
  JJWT, and Lombok dependencies.
- `GatewayApplication.java` exists and the project compiles cleanly.

**Todo List**
1. Create `gateway-service/pom.xml` with:
   - Parent: `spring-boot-starter-parent` 3.2.x
   - Dependencies: `spring-cloud-starter-gateway`, `spring-boot-starter-webflux`,
     `jjwt-api`, `jjwt-impl`, `jjwt-jackson`, `lombok`
   - Spring Cloud BOM (`2023.0.x`) in `dependencyManagement`
2. Create `gateway-service/src/main/java/com/example/gateway/GatewayApplication.java`
   with `@SpringBootApplication` and `main()`.
3. Create `gateway-service/src/main/resources/application.yml` with:
   - `server.port: 8080`
   - Placeholder `jwt.secret` property
   - Empty `spring.cloud.gateway.routes` list (filled in Sub-Task 4)

**Relevant Context**
- Spring Cloud Gateway is reactive (WebFlux); no `spring-boot-starter-web` needed.
- JJWT split into three artifacts: `jjwt-api` (compile), `jjwt-impl` + `jjwt-jackson` (runtime).

**Status** — `[ ] pending`

---

### Sub-Task 2 — Implement `JwtUtil`

**Intent**
Centralise all JWT creation and validation logic in one utility class so filters
and controllers share the same token operations.

**Expected Outcomes**
- `JwtUtil.java` can generate a signed JWT containing `username` and `role`.
- `JwtUtil.java` can parse and validate a token, returning its claims.
- Invalid / expired tokens throw a clear exception.

**Todo List**
1. Create `gateway-service/src/main/java/com/example/gateway/util/JwtUtil.java`.
2. Inject `jwt.secret` from `application.yml` via `@Value`.
3. Implement `generateToken(String username, String role)` — signs with HS256,
   sets 1-hour expiry.
4. Implement `validateToken(String token)` — returns `Claims` or throws
   `JwtException` on failure.
5. Implement `extractUsername(Claims claims)` and `extractRole(Claims claims)`
   convenience methods.

**Relevant Context**
- Use `Jwts.builder()` / `Jwts.parserBuilder()` from `io.jsonwebtoken`.
- Secret should be Base64-encoded in `application.yml` for safety.

**Status** — `[ ] pending`

---

### Sub-Task 3 — Implement `JwtAuthFilter` (Global Gateway Filter)

**Intent**
Intercept every inbound request at the gateway, skip public paths (`/auth/**`),
validate the JWT from the `Authorization: Bearer <token>` header, and either
forward the request with enriched headers or reject with `401`/`403`.

**Expected Outcomes**
- Requests to `/auth/**` pass through untouched.
- Requests with a valid JWT have `X-User-Name` and `X-User-Role` headers injected
  before being forwarded downstream.
- Requests with missing or invalid JWT receive `401 Unauthorized`.
- Requests where role doesn't meet route requirement receive `403 Forbidden`
  (checked via a simple helper method for `/admin/**` routes).

**Todo List**
1. Create `gateway-service/src/main/java/com/example/gateway/filter/JwtAuthFilter.java`.
2. Implement `GlobalFilter` + `Ordered` interfaces (set order to `-1` so it runs first).
3. In `filter(ServerWebExchange, GatewayFilterChain)`:
   a. If path starts with `/auth/`, call `chain.filter(exchange)` and return.
   b. Extract `Authorization` header; if missing → respond `401`.
   c. Strip `Bearer ` prefix and call `JwtUtil.validateToken()`.
   d. On `JwtException` → respond `401`.
   e. If path starts with `/admin/` and role is not `ROLE_ADMIN` → respond `403`.
   f. Mutate the request to add `X-User-Name` and `X-User-Role` headers.
   g. Call `chain.filter(mutatedExchange)`.
4. Add a private helper `respond(ServerWebExchange, HttpStatus)` that sets the
   status and completes the response.

**Relevant Context**
- `ServerWebExchange.getRequest().mutate().header(...)` is the WebFlux way to
  add headers to the forwarded request.
- `exchange.getResponse().setStatusCode(status)` + `response.setComplete()`
  terminates the exchange without a body.

**Status** — `[ ] pending`

---

### Sub-Task 4 — Implement `AuthController` (token issuance)

**Intent**
Provide a `/auth/login` endpoint that accepts demo credentials and returns a
signed JWT — this is the only public endpoint and the entry point for all
learning experiments.

**Expected Outcomes**
- `POST /auth/login` with `{"username":"user","password":"password"}` returns
  `{"token":"<jwt>"}` with `ROLE_USER`.
- `POST /auth/login` with `{"username":"admin","password":"admin"}` returns
  `{"token":"<jwt>"}` with `ROLE_ADMIN`.
- Wrong credentials return `401`.

**Todo List**
1. Create `gateway-service/src/main/java/com/example/gateway/controller/AuthController.java`.
2. Define a `LoginRequest` record with `username` and `password` fields.
3. Define a `LoginResponse` record with a `token` field.
4. Annotate with `@RestController` (note: Spring Cloud Gateway uses WebFlux so
   return `Mono<ResponseEntity<LoginResponse>>`).
5. Map `POST /auth/login`, check credentials against hardcoded map, call
   `JwtUtil.generateToken()`, return response.

**Relevant Context**
- Hard-coded credentials: `user/password → ROLE_USER`, `admin/admin → ROLE_ADMIN`.
- Because Gateway is reactive, use `@RestController` with reactive return types
  (`Mono<>`).

**Status** — `[ ] pending`

---

### Sub-Task 5 — Configure routes in `application.yml` and `RouteConfig`

**Intent**
Define the gateway routes that map inbound paths to downstream microservices,
making it clear which paths are protected and where traffic is forwarded.

**Expected Outcomes**
- `/auth/**` routes to `http://localhost:8080` (handled locally by `AuthController`).
- `/accounts/**` routes to `http://localhost:8081` (the stub `account-service`).
- `/admin/**` routes to `http://localhost:8081` (same stub, role-restricted by filter).
- Routes are visible in `application.yml` for learning/readability.

**Todo List**
1. Populate `spring.cloud.gateway.routes` in `application.yml` with three route
   entries: `auth-route`, `account-route`, `admin-route`.
2. Create `gateway-service/src/main/java/com/example/gateway/config/RouteConfig.java`
   with `@Configuration` — kept minimal (routes already in YAML) but useful as
   a hook for programmatic route customisation later.
3. Add `FallbackController.java` with a `GET /fallback` endpoint returning a
   friendly `503` message string (used later for circuit-breaker fallback).

**Relevant Context**
- YAML routes and Java `RouteLocator` beans can coexist; YAML is preferred for
  readability in a learning context.
- `FallbackController` is a stub for now; no circuit-breaker wiring is in scope.

**Status** — `[ ] pending`

---

### Sub-Task 6 — Scaffold `account-service` stub

**Intent**
Provide a minimal downstream microservice that echoes back the forwarded user
headers, proving that the gateway correctly injects identity after JWT validation.

**Expected Outcomes**
- `account-service` starts on port `8081`.
- `GET /accounts/me` returns a JSON object showing the `X-User-Name` and
  `X-User-Role` values it received.
- `GET /admin/info` returns a similar response (only reachable with `ROLE_ADMIN`
  JWT at the gateway level).

**Todo List**
1. Create `account-service/pom.xml` with `spring-boot-starter-web` and Lombok.
2. Create `AccountServiceApplication.java`.
3. Create `AccountController.java` with two endpoints:
   - `GET /accounts/me` — reads `X-User-Name` and `X-User-Role` from request
     headers, returns them in a map.
   - `GET /admin/info` — same pattern.
4. Set `server.port=8081` in `account-service/src/main/resources/application.properties`.

**Relevant Context**
- This is a plain Spring MVC (servlet) service, not reactive — keep it simple.
- No security config inside `account-service`; all security lives at the gateway.

**Status** — `[ ] pending`

---

## File Map (final state)

```
gateway-service/
├── pom.xml
└── src/main/
    ├── java/com/example/gateway/
    │   ├── GatewayApplication.java
    │   ├── config/
    │   │   └── RouteConfig.java
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   ├── FallbackController.java
    │   │   └── RateLimitController.java   (stub, out of scope for this plan)
    │   ├── filter/
    │   │   └── JwtAuthFilter.java
    │   └── util/
    │       └── JwtUtil.java
    └── resources/
        └── application.yml

account-service/
├── pom.xml
└── src/main/
    ├── java/com/example/account/
    │   ├── AccountServiceApplication.java
    │   └── controller/
    │       └── AccountController.java
    └── resources/
        └── application.properties
```

---

## How to Test (learning walkthrough)

```
# 1. Start account-service
cd account-service && mvn spring-boot:run

# 2. Start gateway-service
cd gateway-service && mvn spring-boot:run

# 3. Login — get a token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password"}'

# 4. Access protected route with token
curl http://localhost:8080/accounts/me \
  -H "Authorization: Bearer <token>"

# 5. Try without token — expect 401
curl http://localhost:8080/accounts/me

# 6. Try admin route with ROLE_USER token — expect 403
curl http://localhost:8080/admin/info \
  -H "Authorization: Bearer <user-token>"

# 7. Login as admin and retry — expect 200
curl -X POST http://localhost:8080/auth/login \
  -d '{"username":"admin","password":"admin"}' -H "Content-Type: application/json"
curl http://localhost:8080/admin/info \
  -H "Authorization: Bearer <admin-token>"
```
