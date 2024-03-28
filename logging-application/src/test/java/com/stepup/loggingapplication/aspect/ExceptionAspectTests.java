package com.stepup.loggingapplication.aspect;

import com.stepup.loggingapplication.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the LoggingAspect class.
 * These tests verify the behavior of logging methods in the LoggingAspect class.
 */
@ExtendWith(MockitoExtension.class)
public class ExceptionAspectTests {

    /**
     * Mock logger object.
     */
    @Mock
    private Logger logger;

    /**
     * Instance of ExceptionAspect class being tested.
     */
    private ExceptionAspect exceptionAspect;

    /**
     * Setup method executed before each test.
     * Initializes the exceptionAspect object and sets the logger.
     *
     * @throws Exception   if a field with the specified name is not found
     */
    @BeforeEach
    public void setUp() throws Exception {
        exceptionAspect = new ExceptionAspect();
        setLogger(exceptionAspect, logger);
    }

    /**
     * Test method for verifying handling exceptions.
     */
    @Test
    public void testHandleExceptions() {
        // Given
        Exception exception = new NotFoundException("Test Exception");

        // When
        ResponseEntity<String> responseEntity = exceptionAspect.handleExceptions(exception);

        // Then
        verify(logger).error("Test Exception");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Test Exception", responseEntity.getBody());
    }

    /**
     * Test method for verifying handling exceptions.
     */
    @Test
    public void testHandleExceptions_IllegalArgumentException() {
        // Given
        Exception exception = new IllegalArgumentException("Test Exception");

        // When
        ResponseEntity<String> responseEntity = exceptionAspect.handleExceptions(exception);

        // Then
        verify(logger).error("Test Exception");
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Test Exception", responseEntity.getBody());
    }

    /**
     * Sets the logger field of the ExceptionAspect instance using reflection.
     *
     * @param aspect ExceptionAspect instance
     * @param logger Logger to set
     * @throws NoSuchFieldException   if a field with the specified name is not found
     * @throws IllegalAccessException if the field cannot be accessed
     */
    private void setLogger(ExceptionAspect aspect, Logger logger) throws NoSuchFieldException, IllegalAccessException {
        Field field = aspect.getClass().getDeclaredField("logger");
        field.setAccessible(true);
        field.set(aspect, logger);
    }
}