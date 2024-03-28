package com.stepup.loggingapplication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Custom Spring Security filter for handling JWT authentication.
 * This class is annotated with Spring's @Component, making it a Spring Bean.
 * It extends OncePerRequestFilter, ensuring that the filter is executed only once per request.
 * It is constructed using Lombok's @RequiredArgsConstructor, injecting the required JwtDecoder and JwtToPrincipalConverter.
 * The filter is responsible for extracting and validating JWT tokens from incoming requests.
 *
 * @see org.springframework.stereotype.Component
 * @see org.springframework.web.filter.OncePerRequestFilter
 * @see JwtDecoder
 * @see JwtToPrincipalConverter
 * @see UserPrincipalAuthenticationToken
 * @see org.springframework.security.core.context.SecurityContextHolder
 * @see org.springframework.security.core.Authentication
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 * @see org.springframework.util.StringUtils
 * @see java.util.Optional
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * The decoder for validating and decoding JWT tokens.
     */
    private final JwtDecoder jwtDecoder;

    /**
     * The converter for converting a JWT to a UserPrincipal.
     */
    private final JwtToPrincipalConverter jwtToPrincipalConverter;


    /**
     * Filters the request to extract and validate JWT tokens.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain to execute.
     * @throws jakarta.servlet.ServletException If an exception occurs during the filter processing.
     * @throws java.io.IOException      If an I/O exception occurs during the filter processing.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }


    /**
     * Extracts the JWT token from the request's Authorization header.
     *
     * @param request The HTTP servlet request.
     * @return An Optional containing the extracted JWT token or empty if not found.
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }

        return Optional.empty();
    }
}
