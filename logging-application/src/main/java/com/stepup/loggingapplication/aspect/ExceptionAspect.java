package com.stepup.loggingapplication.aspect;

import com.stepup.loggingapplication.exception.DuplicateEntityException;
import com.stepup.loggingapplication.exception.InvalidResetTokenException;
import com.stepup.loggingapplication.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Aspect class for handling exceptions thrown within the application.
 * It logs the exception message and determines the appropriate HTTP status code to return.
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class ExceptionAspect {

    private final Logger logger;

    private final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap;


    /**
     * Constructor to initialize the logger and exception status mapping.
     */
    public ExceptionAspect() {
        this.logger = LogManager.getLogger(ExceptionAspect.class);
        this.exceptionStatusMap = initializeExceptionStatusMap();
    }

    /**
     * Advice method to handle exceptions thrown by service methods.
     *
     * @param ex The exception thrown by the method.
     * @return ResponseEntity containing the error message and HTTP status code.
     */
    @AfterThrowing(pointcut = "execution(* com.stepup.loggingapplication.service.*.*(..)) " +
                              "|| execution(* com.stepup.loggingapplication.controller.*.*(..))", throwing = "ex")
    public ResponseEntity<String> handleExceptions(Exception ex) {
        HttpStatus status = determineHttpStatus(ex);

        // Log the exception message
        logger.error(ex.getMessage());

        // Set the error message to be returned to the ResponseEntity
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Unknown error occurred";

        return new ResponseEntity<>(errorMessage, status);
    }

    /**
     * Determines the appropriate HTTP status code for the given exception.
     *
     * @param ex The exception thrown by the method.
     * @return The HTTP status code.
     */
    private HttpStatus determineHttpStatus(Exception ex) {
        return exceptionStatusMap.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Initializes the mapping of exception classes to HTTP status codes.
     *
     * @return The mapping of exception classes to HTTP status codes.
     */
    private Map<Class<? extends Exception>, HttpStatus> initializeExceptionStatusMap() {
        Map<Class<? extends Exception>, HttpStatus> map = new HashMap<>();
        map.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        map.put(jakarta.persistence.EntityNotFoundException.class, HttpStatus.NOT_FOUND);
        map.put(DuplicateEntityException.class, HttpStatus.CONFLICT);
        map.put(IllegalArgumentException.class, HttpStatus.CONFLICT);
        map.put(InvalidResetTokenException.class, HttpStatus.CONFLICT);
        return map;
    }
}
