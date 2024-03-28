package com.stepup.loggingapplication.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Component that handles unauthorized access by sending an HTTP 401 Unauthorized response.
 * This class is annotated with Spring's @Component, making it a Spring Bean.
 * It implements Spring Security's AuthenticationEntryPoint interface to customize behavior
 * when an unauthenticated user attempts to access a secured resource.
 *
 * @see org.springframework.stereotype.Component
 * @see org.springframework.security.web.AuthenticationEntryPoint
 * @see org.springframework.security.core.AuthenticationException
 * @see java.io.IOException
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * Invoked when an unauthenticated user attempts to access a secured resource.
     * Sends an HTTP 401 Unauthorized response.
     *
     * @param request       The HTTP servlet request.
     * @param response      The HTTP servlet response.
     * @param authException The authentication exception that occurred.
     * @throws java.io.IOException      If an I/O exception occurs during the handling.
     * @throws jakarta.servlet.ServletException If a servlet exception occurs during the handling.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
