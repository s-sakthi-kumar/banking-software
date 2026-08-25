package com.example.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JwtUtil — centralises all JWT creation and validation logic.
 *
 * <p>How it works (learning notes):
 * <ol>
 *   <li>A secret key (Base64-encoded in application.yml) is decoded once at startup.</li>
 *   <li>{@link #generateToken} builds a signed JWT with a {@code role} claim and a 1-hour expiry.</li>
 *   <li>{@link #validateToken} parses the token, verifies the signature, and checks expiry.
 *       It throws a {@link JwtException} on any failure — the filter catches this to return 401.</li>
 * </ol>
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    /**
     * The constructor decodes the Base64 secret from application.yml and builds
     * an HMAC-SHA256 {@link SecretKey} once — reused for every token operation.
     */
    public JwtUtil(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {

        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    /**
     * Generate a signed JWT for the given username and role.
     *
     * @param username the authenticated user's name (stored as JWT subject)
     * @param role     the user's role, e.g. {@code ROLE_USER} or {@code ROLE_ADMIN}
     * @return compact serialised JWT string
     */
    public String generateToken(String username, String role) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)           // "sub" claim
                .claim("role", role)            // custom "role" claim
                .setIssuedAt(now)               // "iat" claim
                .setExpiration(expiry)          // "exp" claim
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Parse and validate a JWT string.
     *
     * @param token the compact serialised JWT
     * @return parsed {@link Claims} (subject, role, expiry, etc.)
     * @throws JwtException if the token is malformed, has an invalid signature,
     *                      or has expired
     */
    public Claims validateToken(String token) {
        // parserBuilder() verifies the signature AND checks expiry automatically.
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Convenience: extract username (subject) from already-validated claims. */
    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    /** Convenience: extract role from already-validated claims. */
    public String extractRole(Claims claims) {
        return claims.get("role", String.class);
    }
}
