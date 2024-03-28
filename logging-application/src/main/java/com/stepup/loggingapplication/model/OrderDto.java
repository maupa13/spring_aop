package com.stepup.loggingapplication.model;

import lombok.Builder;
import lombok.Getter;

/**
 * A data transfer object (DTO) representing a request to update an order.
 * It is annotated with Lombok's @Getter and @Builder to automatically generate getters and a builder.
 */
@Getter
@Builder
public class OrderDto {

    /**
     * The id of the order.
     */
    private final Long id;

    /**
     * The title of the order.
     */
    private final String title;

    /**
     * The description of the order.
     */
    private String description;

    /**
     * The status of the order.
     */
    private final String status;
}
