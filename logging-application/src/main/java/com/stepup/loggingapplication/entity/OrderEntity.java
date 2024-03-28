package com.stepup.loggingapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing an order in the application.
 * This class is annotated with Lombok's @Getter and @Setter to automatically generate getters and setters.
 * It is also annotated as a JPA Entity, mapped to the "orders" table and constructors.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the order.
     */
    @Column(nullable = false)
    @NotNull(message = "Title is mandatory")
    private String title;

    /**
     * The description of the order.
     */
    @NotNull(message = "Description is mandatory")
    private String description;

    /**
     * The status of the order.
     */
    @Column(nullable = false)
    @NotNull(message = "Status is mandatory")
    private String status;

    /**
     * The category to which the product belongs.
     * and is configured for eager fetching to load the associated category along with the product.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userEntity_id", nullable = false)
    private UserEntity userEntity;

}
