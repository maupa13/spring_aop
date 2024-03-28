package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A data transfer object (DTO) representing a request to update user entity.
 * This class is annotated with Lombok's @Getter, @Setter and @Builder
 * to automatically generate getters, setters and a builder.
 */
@Getter
@Setter
@Builder
public class UserRequestUpdateDto {

    /**
     * The name of the user requesting updating.
     */
    private String name;

    /**
     * The email of the user requesting updating.
     */
    private String email;

    /**
     * The password of the user requesting updating.
     */
    private String password;
}
