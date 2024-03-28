package com.stepup.loggingapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * Entity class representing a user in the application.
 * This class is annotated with Lombok's @Getter and @Setter to automatically generate getters and setters.
 * It is also annotated as a JPA Entity, mapped to the "users" table and constructors.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the user.
     */
    @Column(nullable = false, unique = true)
    @NotNull(message = "Name is mandatory")
    private String name;

    /**
     * The email address of the user.
     */
    @Column(nullable = false, unique = true)
    @NotNull(message = "Email is mandatory")
    private String email;

    /**
     * The password associated with the user's account.
     */
    @Column(nullable = false)
    @NotNull(message = "Password is mandatory")
    private String password;

    /**
     * The role assigned to the user.
     */
    @Column(nullable = false)
    @NotNull(message = "Title is mandatory")
    private String role;

    /**
     * The list of products associated with the category.
     * This field is mapped as a one-to-many relationship with
     * the {@link com.stepup.loggingapplication.entity.OrderEntity} entity,
     * and is configured for lazy fetching to improve performance.
     */
    @OneToMany(mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderEntity> orders;
}
