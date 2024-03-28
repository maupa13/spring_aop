package com.stepup.loggingapplication.service;

import com.stepup.loggingapplication.model.LoginResponseDto;
import com.stepup.loggingapplication.security.JwtIssuer;
import com.stepup.loggingapplication.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for authentication-related operations in the application.
 * This class is annotated with Spring's @Service, making it a Spring service bean.
 * It is annotated with Lombok's {@code @RequiredArgsConstructor} for constructor injection.
 * It uses a JwtIssuer for issuing JWTs and an AuthenticationManager for user authentication.
 *
 * @see org.springframework.stereotype.Service
 * @see com.stepup.loggingapplication.security.JwtIssuer
 * @see org.springframework.security.authentication.AuthenticationManager
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 * @see org.springframework.security.core.context.SecurityContextHolder
 * @see org.springframework.security.core.Authentication
 * @see com.stepup.loggingapplication.security.UserPrincipal
 * @see com.stepup.loggingapplication.model.LoginResponseDto
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * JwtIssuer for issuing JWT tokens.
     */
    private final JwtIssuer jwtIssuer;

    /**
     * AuthenticationManager for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Attempts user login by authenticating the provided credentials and issuing a JWT token upon success.
     *
     * @param name    The user's name.
     * @param password The user's password.
     * @return A LoginResponseDto containing the JWT token.
     */
    public LoginResponseDto attemptLogin(String name, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .userId(principal.getUserId())
                .email(principal.getEmail())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build());

        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
