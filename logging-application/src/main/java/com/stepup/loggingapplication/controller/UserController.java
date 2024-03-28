package com.stepup.loggingapplication.controller;

import com.stepup.loggingapplication.entity.UserEntity;
import com.stepup.loggingapplication.exception.NotFoundException;
import com.stepup.loggingapplication.model.UserRequestUpdateDto;
import com.stepup.loggingapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling user-related operations.
 * This class is annotated with Spring's @RestController and @RequestMapping.
 * Base URL is "/users".
 * It utilizes the {@link UserService} to perform operations on user entities.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users in the system.
     * Requires the 'ADMIN' role for access.
     *
     * @return ResponseEntity containing the list of UserEntity objects and HTTP status OK (200).
     */
    @GetMapping("/")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their ID.
     * Requires the 'ADMIN' role for access.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the UserEntity if found, otherwise returns HTTP status NOT FOUND (404).
     */
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found. User id: " + id));

        return ResponseEntity.ok(userEntity);
    }

    /**
     * Endpoint for updating an existing user.
     *
     * @param id The ID of the user to be updated.
     * @param userRequestUpdateDto The {@code UserRequestUpdateDto} object containing updated user details.
     * @return A {@code ResponseEntity} with the updated {@code UserEntity} and HTTP status code 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                                 @RequestBody UserRequestUpdateDto userRequestUpdateDto) {
        UserEntity updatedUser = userService.updateUser(id, userRequestUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user by ID.
     * Requires the 'ADMIN' role for access.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with HTTP status OK (200).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        UserEntity deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }

    /**
     * Endpoint for creating an admin user.
     * Requires the 'ADMIN' role for access.
     *
     * @param id The ID of the user to be updated to admin role.
     * @param userRequestUpdateDto The {@code UserRequestUpdateDto} object containing updated user details to admin role.
     * @return A {@code ResponseEntity} with the created {@code UserEntity} and HTTP status code 201 (CREATED).
     */
    @PutMapping ("/admin/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> updateUserToAdmin(@PathVariable Long id,
                                                    @RequestBody UserRequestUpdateDto userRequestUpdateDto) {
        UserEntity updatedUserToAdmin = userService.updateUserToAdmin(id, userRequestUpdateDto);
        return ResponseEntity.ok(updatedUserToAdmin);
    }
}
