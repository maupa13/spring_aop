package com.stepup.loggingapplication.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Custom authentication token representing the authenticated user principal.
 * This class extends Spring Security's AbstractAuthenticationToken and is used
 * to encapsulate the authenticated user's principal (UserPrincipal) during authentication.
 *
 * @see org.springframework.security.authentication.AbstractAuthenticationToken
 * @see UserPrincipal
 */
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * The authenticated user principal.
     */
    private final UserPrincipal principal;

    /**
     * Constructs a UserPrincipalAuthenticationToken with the provided user principal.
     * Marks the token as authenticated.
     *
     * @param principal The authenticated user principal.
     */
    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    /**
     * {@inheritDoc}
     * <p>This method always returns {@code null} as credentials are not applicable for this type of token.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return The authenticated user principal.
     */
    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }
}
