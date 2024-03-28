package com.stepup.loggingapplication.security;

import com.stepup.loggingapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Custom implementation of Spring Security's UserDetailsService interface.
 * This class is annotated with Spring's @Component, making it a Spring Bean.
 * It is constructed using Lombok's @RequiredArgsConstructor, injecting the required UserService.
 * The class is responsible for loading user details during authentication.
 *
 * @see org.springframework.stereotype.Component
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see UserPrincipal
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.core.userdetails.UsernameNotFoundException
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    /**
     * The service responsible for interacting with user data.
     */
    private final UserService userService;

    /**
     * Loads user details by username during authentication.
     *
     * @param name The username for which user details should be loaded.
     * @return A UserDetails object representing the authenticated user.
     * @throws UsernameNotFoundException If the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var user = userService.findByName(name).orElseThrow(() ->
                new UsernameNotFoundException("User not found with name: " + name));

        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .build();
    }
}
