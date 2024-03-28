package com.stepup.loggingapplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Logging Application.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
public class LoggingApplication {

    private static final Logger logger = LogManager.getLogger(LoggingApplication.class);


    /**
     * Entry point of the Logging Application.
     * It initializes the Log4j2 configuration, logs the application startup message,
     * and starts the Spring Boot application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Set the system property to specify the Log4j2 configuration file
        System.setProperty("log4j.configurationFile", "log4j2.xml");

        logger.info("Application started");

        SpringApplication.run(LoggingApplication.class, args);
    }
}
