package com.stepup.loggingapplication.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component responsible for converting a decoded JWT (JSON Web Token) into a UserPrincipal.
 * This class is annotated with Spring's @Component, making it a Spring Bean.
 * It is responsible for extracting user information from the decoded JWT and creating a UserPrincipal.
 *
 * @see org.springframework.stereotype.Component
 * @see com.auth0.jwt.interfaces.DecodedJWT
 * @see UserPrincipal
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 * @see lombok.Builder
 */
@Component
public class JwtToPrincipalConverter {

    /**
     * Converts a decoded JWT to a UserPrincipal.
     *
     * @param jwt The decoded JWT.
     * @return The UserPrincipal created from the JWT.
     */
    public UserPrincipal convert(DecodedJWT jwt) {
        var authorityList = getClaimOrEmptyList(jwt, "authorities").stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return UserPrincipal.builder()
                .userId(Long.parseLong(jwt.getSubject()))
                .email(jwt.getClaim("email").asString())
                .authorities( authorityList )
                .build();
    }

    /**
     * Retrieves a claim from the decoded JWT or returns an empty list if the claim is null.
     *
     * @param jwt   The decoded JWT.
     * @param claim The claim to retrieve.
     * @return The list of values for the specified claim or an empty list if the claim is null.
     */
    private List<String> getClaimOrEmptyList(DecodedJWT jwt, String claim) {
        if (jwt.getClaim(claim).isNull()) return List.of();

        return jwt.getClaim(claim).asList(String.class);
    }
}
