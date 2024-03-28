package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;

/**
 * A data transfer object (DTO) representing a request for user login.
 * It is annotated with Lombok's @Getter and @Builder to automatically generate getters and a builder.
 */
@Getter
@Builder
public class LoginRequestDto {

    /**
     * The email address associated with the user's account.
     */
    private final String email;

    /**
     * The password provided by the user for authentication.
     */
    private final String password;
}
