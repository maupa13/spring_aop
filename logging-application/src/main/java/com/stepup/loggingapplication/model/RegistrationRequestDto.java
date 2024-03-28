package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;


/**
 * A data transfer object (DTO) representing a registration request for a new user.
 * It is annotated with Lombok's @Getter and @Builder to automatically generate getters and a builder.
 */
@Getter
@Builder
public class RegistrationRequestDto {

    /**
     * The name of the user.
     */
    private final String name;

    /**
     * The email address of the user.
     */
    private final String email;

    /**
     * The password chosen by the user for authentication.
     */
    private final String password;

    /**
     * The role assigned to the user during registration.
     */
    private final String role;
}
