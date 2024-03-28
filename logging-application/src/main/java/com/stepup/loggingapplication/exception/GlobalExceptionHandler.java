package com.stepup.loggingapplication.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a controller advice class that provides centralized exception handling
 * for specific exception types.
 *
 * This class includes an {@code @ExceptionHandler} method to handle exceptions of types
 * {@link NotFoundException} and {@link jakarta.persistence.EntityNotFoundException}. It returns a consistent
 * {@link org.springframework.http.ResponseEntity} with a 404 (Not Found) status and the error message from the exception.
 * {@link DuplicateEntityException} with a 409 (Conflict) status and the error message from the exception.
 *
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see org.springframework.http.ResponseEntity
 * @see NotFoundException
 * @see jakarta.persistence.EntityNotFoundException
 * @see DuplicateEntityException
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of types {@link NotFoundException} and {@link jakarta.persistence.EntityNotFoundException}.
     * Returns a {@link org.springframework.http.ResponseEntity} with a 404 (Not Found) status and the error message from the exception.
     *
     * @param ex The exception to handle.
     * @return A {@link org.springframework.http.ResponseEntity} with a 404 status and the error message.
     */
    @ExceptionHandler({NotFoundException.class,
            EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type {@link DuplicateEntityException},
     * {@link IllegalArgumentException} and {@link InvalidResetTokenException}.
     * Returns a {@link org.springframework.http.ResponseEntity} with a 409 (Conflict) status and the error message from the exception.
     *
     * @param ex The exception to handle.
     * @return A {@link org.springframework.http.ResponseEntity} with a 409 status and the error message.
     */
    @ExceptionHandler({DuplicateEntityException.class,
            IllegalArgumentException.class,
            InvalidResetTokenException.class})
    public ResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}