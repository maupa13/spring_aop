package com.stepup.loggingapplication.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * UserPrincipal class representing the authenticated user's principal details.
 * This class is annotated with Lombok's @Getter and @Builder for automatic generation of getter methods and a builder.
 * It implements Spring Security's UserDetails interface to provide user-related details for authentication and authorization.
 *
 * @see lombok.Getter
 * @see lombok.Builder
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.core.GrantedAuthority
 * @see com.fasterxml.jackson.annotation.JsonIgnore
 * @see java.util.List
 * @see java.util.Collection
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 */
@Getter
@Builder
public class UserPrincipal implements UserDetails {

    /**
     * The unique identifier for the user.
     */
    private final Long userId;

    /**
     * The email address of the user.
     */
    private final String email;

    /**
     * The hashed password of the user. Marked with @JsonIgnore to prevent serialization.
     */
    @JsonIgnore
    private final String password;

    /**
     * The authorities (roles) granted to the user.
     */
    private final List<? extends GrantedAuthority> authorities;

    /**
     * Returns the authorities granted to the user.
     *
     * @return The list of authorities.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The password.
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return The username.
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return Always returns true (account never expires).
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return Always returns true (account never locked).
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return Always returns true (credentials never expire).
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return Always returns true (user always enabled).
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
