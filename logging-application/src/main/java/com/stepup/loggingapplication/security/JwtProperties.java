package com.stepup.loggingapplication.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration properties class for JWT (JSON Web Token) settings.
 * This class is annotated with Lombok's @Getter and @Setter for automatic generation of getter and setter methods.
 * It is annotated with Spring's @Configuration, making it a configuration class.
 * The class is configured using properties prefixed with "application.security.jwt".
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @see org.springframework.beans.factory.annotation.Value
 * @see java.time.Duration
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("application.security.jwt")
public class JwtProperties {

    /**
     * The secret key used for signing and verifying JWT tokens.
     */
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    /**
     * The duration of the JWT token's validity period.
     */
    @Value("${application.security.jwt.token-duration}")
    private Duration tokenDuration;
}
