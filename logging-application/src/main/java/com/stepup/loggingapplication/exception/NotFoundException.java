package com.stepup.loggingapplication.exception;

/**
 * Exception thrown when a resource is not found.
 * This exception is typically used to indicate that an expected resource,
 * such as a database record could not be located.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotFoundException(String message){
        super(message);
    }
}
