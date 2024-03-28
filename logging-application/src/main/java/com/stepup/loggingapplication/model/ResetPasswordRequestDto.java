package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;

/**
 * A data transfer object (DTO) representing a request to reset a user's password.
 * It is annotated with Lombok's @Getter and @Builder to automatically generate getters and a builder.
 */
@Getter
@Builder
public class ResetPasswordRequestDto {

    /**
     * The email address of the user requesting a password reset.
     */
    private final String email;

    /**
     * The user's old password, if applicable (for security checks).
     */
    private final String oldPassword;

    /**
     * The new password the user wants to set.
     */
    private final String newPassword;
}
