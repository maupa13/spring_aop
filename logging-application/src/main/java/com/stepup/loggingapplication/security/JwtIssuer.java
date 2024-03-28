package com.stepup.loggingapplication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Component responsible for issuing (creating) JWT tokens.
 * This class is annotated with Spring's @Component, making it a Spring Bean.
 * It is constructed using Lombok's @RequiredArgsConstructor, injecting the required JwtProperties.
 * The class is responsible for creating JWT tokens based on the provided user information and configuration.
 *
 * @see org.springframework.stereotype.Component
 * @see JwtProperties
 * @see com.auth0.jwt.JWT
 * @see com.auth0.jwt.algorithms.Algorithm
 * @see lombok.Builder
 * @see lombok.Getter
 * @see java.time.Instant
 * @see java.util.List
 */
@Component
@RequiredArgsConstructor
public class JwtIssuer {

    /**
     * The configuration properties for JWT, including the secret key.
     */
    private final JwtProperties properties;

    /**
     * Issues (creates) a JWT token based on the provided user request.
     *
     * @param request The user request containing user information.
     * @return The JWT token issued for the user.
     * @see com.auth0.jwt.JWT
     * @see com.auth0.jwt.algorithms.Algorithm
     */
    public String issue(Request request) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf(request.userId))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(properties.getTokenDuration()))
                .withClaim("email", request.getEmail())
                .withClaim("authorities", request.getRoles())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    /**
     * Request class for providing user information when issuing a JWT token.
     * This class is annotated with Lombok's @Builder for easy and readable object creation.
     *
     * @see lombok.Builder
     * @see lombok.Getter
     */
    @Getter
    @Builder
    public static class Request {
        private final Long userId;
        private final String email;
        private final List<String> roles;
    }
}
