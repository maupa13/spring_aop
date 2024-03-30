package com.stepup.loggingapplication.aspect;

import com.stepup.loggingapplication.ConfigEnvironmentTest;
import com.stepup.loggingapplication.model.RegistrationRequestDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the {@link LoggingAspect} class.
 * <p>
 * These tests verify the logging behavior of the {@link LoggingAspect} by intercepting method calls and capturing
 * log statements. The tests are configured to run with the "dev" profile, and they set the logging level for the
 * {@link LoggingAspect} to DEBUG to ensure that logging statements are captured for verification.
 */
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"logging.level.com.stepup.loggingapplication.aspect.LoggingAspect=DEBUG"})
public class LoggingAspectTests extends ConfigEnvironmentTest {

    @LocalServerPort
    private int port;

    private ByteArrayOutputStream logCapture;

    /**
     * Sets up the test environment before each test method execution.
     * <p>
     * This method redirects the standard output to a {@link ByteArrayOutputStream} to capture logging statements
     * for verification.
     */
    @BeforeEach
    public void setUp() {
        // Redirect logs to a ByteArrayOutputStream
        logCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logCapture));
    }

    /**
     * Cleans up the test environment after each test method execution.
     * <p>
     * This method restores the standard output after logging verification is completed.
     */
    @AfterEach
    public void tearDown() {
        // Restore standard output
        System.setOut(System.out);
    }

    /**
     * Tests the logging behavior of the {@link LoggingAspect} for method entry and exit.
     * <p>
     * This test case invokes a method that is intercepted by the {@link LoggingAspect} and verifies that the aspect
     * logs the appropriate method entry and exit messages.
     */
    @Test
    void logMethod() {
        // Given
        RegistrationRequestDto user = RegistrationRequestDto.builder()
                .email("Email 1")
                .name("Name 1")
                .password("Password 1")
                .role("ROLE_USER")
                .build();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(HttpStatus.OK.value());

        // Then
        String logs = logCapture.toString();
        assertTrue(logs.contains("Entering method:"));
        assertTrue(logs.contains("Exiting method:"));
    }
}
