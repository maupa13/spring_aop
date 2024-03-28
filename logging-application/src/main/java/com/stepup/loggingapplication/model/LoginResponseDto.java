package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * A data transfer object (DTO) representing a response to a user login request.
 * It is annotated with Lombok's @Getter, @Builder, and @ToString
 * to automatically generate getters, a builder and a toString method.
 */
@Getter
@Builder
@ToString
public class LoginResponseDto {

    /**
     * The authentication token associated with a successful login.
     */
    private String token;
}
