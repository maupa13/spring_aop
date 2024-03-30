package com.stepup.loggingapplication.aspect;

import com.stepup.loggingapplication.ConfigEnvironmentTest;
import com.stepup.loggingapplication.entity.UserEntity;
import com.stepup.loggingapplication.model.LoginRequestDto;
import com.stepup.loggingapplication.repository.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the {@link ExceptionAspect} class.
 * <p>
 * These tests verify that the aspect properly handles exceptions thrown by service and controller methods.
 * <p>
 * The tests are configured to run with the "dev" profile, and they set the logging level for the {@link ExceptionAspect}
 * to DEBUG to ensure that logging statements are captured for verification.
 */
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"logging.level.com.stepup.loggingapplication.aspect.ExceptionAspect=DEBUG"})
public class ExceptionAspectTests extends ConfigEnvironmentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

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
     * Tests the logging behavior of the {@link ExceptionAspect} for unauthorized login attempts.
     * <p>
     * This test case simulates a login attempt with invalid credentials and verifies that the aspect logs the
     * appropriate exception message.
     */
    @Test
    void logMethod() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123");
        userEntity.setName("Name");
        userEntity.setEmail("email@");
        userEntity.setRole("ROLE_USER");
        userEntity.setOrders(new ArrayList<>());

        userRepository.save(userEntity);

        LoginRequestDto logingRequestDto = LoginRequestDto.builder()
                .email("email@")
                .password("WRONG")
                .build();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(logingRequestDto)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        // Then
        String logs = logCapture.toString();
        assertTrue(logs.contains("Exception"));
    }
}
