package com.stepup.loggingapplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security in the web application.
 *
 * This class uses annotations like @Configuration, @EnableWebSecurity to declare that it provides
 * configuration information and enables Spring Security for the application.
 *
 * Uses @RequiredArgsConstructor to generate a constructor injecting the required dependencies.
 *
 * The class defines three main beans:
 * 1. applicationSecurity.
 * 2. passwordEncoder.
 * 3. authenticationManager.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**
     * The JwtAuthenticationFilter responsible for processing JWTs during the authentication process.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * The CustomUserDetailService providing user details for authentication.
     */
    private final CustomUserDetailService customUserDetailService;

    /**
     * The UnauthorizedHandler handling unauthorized access to secure resources.
     */
    private final UnauthorizedHandler unauthorizedHandler;

    /**
     * Configures the security settings for the application.
     *
     * @param http the HttpSecurity object to configure security settings.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {

        // Configures JWT authentication filter and various security settings
        // such as CORS, CSRF, session management, exception handling, and authorization rules.
        // Allows public access to certain paths like Swagger documentation and authentication-related endpoints.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .exceptionHandling(h -> h.authenticationEntryPoint(unauthorizedHandler))
            .securityMatcher("/**")
            .authorizeHttpRequests(registry -> registry
                    .requestMatchers("/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v2/api-docs/**").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/auth/reset-password").permitAll()
                    .requestMatchers("/auth/register").permitAll()
                    .requestMatchers("/orders").authenticated()
                    .requestMatchers("/users").hasRole("ADMIN")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/user").hasRole("USER")
                    .anyRequest().authenticated()
            );

        return http.build();
    }

    /**
     * Configures the password encoder for the application.
     *
     * @return a BCryptPasswordEncoder as the default password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager for the application.
     *
     * @param http the HttpSecurity object to configure authentication manager.
     * @return the configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        // Configures the authentication manager with the custom UserDetailsService and password encoder.
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        return builder.build();
    }
}
