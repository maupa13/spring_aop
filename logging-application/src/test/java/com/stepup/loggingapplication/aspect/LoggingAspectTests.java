package com.stepup.loggingapplication.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the LoggingAspect class.
 * These tests verify the behavior of logging methods in the LoggingAspect class.
 */
@ExtendWith(MockitoExtension.class)
public class LoggingAspectTests {

    /**
     * Mock logger object.
     */
    @Mock
    private Logger logger;

    /**
     * Mock joinPoint object.
     */
    @Mock
    private JoinPoint joinPoint;

    /**
     * Instance of LoggingAspect class being tested.
     */
    private LoggingAspect loggingAspect;

    /**
     * Setup method executed before each test.
     * Initializes the loggingAspect object and sets the logger.
     *
     * @throws NoSuchFieldException   if a field with the specified name is not found
     * @throws IllegalAccessException if the field cannot be accessed
     */
    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        loggingAspect = new LoggingAspect();
        setLogger(loggingAspect, logger);
    }

    /**
     * Test method for verifying the log entry.
     */
    @Test
    public void testLogMethodEntry() {
        // Configure the mock JoinPoint
        MethodSignature methodSignature = mock(MethodSignature.class);
        when(methodSignature.toShortString()).thenReturn("methodName"); // Mock the behavior of toShortString()
        when(joinPoint.getSignature()).thenReturn(methodSignature);

        // When
        loggingAspect.logMethodEntry(joinPoint);

        // Then
        verify(logger).info("Entering method: {}", "methodName");
    }

    /**
     * Test method for verifying the log exit.
     */
    @Test
    public void testLogMethodExit() {
        // Configure the mock JoinPoint
        MethodSignature methodSignature = mock(MethodSignature.class);
        when(methodSignature.toShortString()).thenReturn("methodName"); // Mock the behavior of toShortString()
        when(joinPoint.getSignature()).thenReturn(methodSignature);

        // When
        loggingAspect.logMethodExit(joinPoint);

        // Then
        verify(logger).info("Exiting method: {}", "methodName");
    }

    /**
     * Sets the logger field of the LoggingAspect instance using reflection.
     *
     * @param loggingAspect LoggingAspect instance
     * @param logger        Logger to set
     * @throws NoSuchFieldException   if a field with the specified name is not found
     * @throws IllegalAccessException if the field cannot be accessed
     */
    private void setLogger(LoggingAspect loggingAspect, Logger logger) throws NoSuchFieldException, IllegalAccessException {
        Field field = LoggingAspect.class.getDeclaredField("logger");
        field.setAccessible(true);
        field.set(loggingAspect, logger);
    }
}