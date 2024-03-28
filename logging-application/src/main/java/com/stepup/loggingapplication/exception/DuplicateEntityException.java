package com.stepup.loggingapplication.exception;

/**
 * Exception thrown when there is duplicate entity.
 * This exception is typically used to indicate that an expected resource,
 * such as a database record could not be located.
 */
public class DuplicateEntityException extends RuntimeException {

    /**
     * Constructs a new DuplicateEntityException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DuplicateEntityException(String message){
        super(message);
    }
}
