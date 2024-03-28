package com.stepup.loggingapplication.config;

import com.stepup.loggingapplication.entity.OrderEntity;
import com.stepup.loggingapplication.entity.UserEntity;
import com.stepup.loggingapplication.model.RegistrationRequestDto;
import com.stepup.loggingapplication.model.UserRequestUpdateDto;
import com.stepup.loggingapplication.repository.OrderRepository;
import com.stepup.loggingapplication.repository.UserRepository;
import com.stepup.loggingapplication.service.AuthService;
import com.stepup.loggingapplication.service.RegistrationService;
import com.stepup.loggingapplication.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Load data in database while running application.
 *
 * @see UserService The service for user-related operations.
 * @see RegistrationService The service for user registration.
 * @see AuthService The service for authentication operations.
 * @see UserRepository The service for user repository operations.
 * @see OrderRepository The service for order repository operations.
 */
@AllArgsConstructor
@Component
public class LoadDataConfig {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final AuthService authService;
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private static final Logger logger = LogManager.getLogger(LoadDataConfig.class);

    private static final String ADMIN_EMAIL = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "AdminPassword";
    private static final String ADMIN_NAME = "Admin";

    private static final String USER_EMAIL_TWO = "user2@mail.com";
    private static final String USER_PASSWORD_TWO = "UserPassword2";
    private static final String USER_NAME_TWO = "User2";

    private static final String USER_EMAIL_ONE = "user1@mail.com";
    private static final String USER_PASSWORD_ONE = "UserPassword1";
    private static final String USER_NAME_ONE = "User1";

    /**
     * Custom {@link org.springframework.boot.CommandLineRunner} bean that performs initial setup during application startup.
     * It registers an admin, users and orders if they do not exist, and prints authentication tokens for both.
     *
     * @return {@link org.springframework.boot.CommandLineRunner} instance that performs the necessary setup.
     */
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            registerAdminAndPrintToken();
            registerUsersAndPrintToken();
            createOrderEntities();
        };
    }

    /**
     * Register an admin, if him does not exist, and prints JWT authentication token.
     */
    private void registerAdminAndPrintToken() {
        userService.findByName(ADMIN_NAME).ifPresentOrElse(
                adminEntity -> logger.info("Admin already exists. Skipping registration."),
                () -> {
                    // Register new admin
                    RegistrationRequestDto admin = RegistrationRequestDto.builder()
                            .name(ADMIN_NAME)
                            .email(ADMIN_EMAIL)
                            .password(ADMIN_PASSWORD)
                            .build();
                    registrationService.registerUser(admin);

                    // Change role from "ROLE_USER" to "ROLE_ADMIN"
                    UserRequestUpdateDto userRequestUpdateDto = UserRequestUpdateDto.builder()
                            .email(ADMIN_EMAIL)
                            .name(ADMIN_NAME)
                            .password(ADMIN_PASSWORD)
                            .build();
                    Long adminId = userRepository.findByEmailIgnoreCase(ADMIN_EMAIL).getId();
                    userService.updateUserToAdmin(adminId, userRequestUpdateDto);
                }
        );
        logger.info("Token Admin: " + authService.attemptLogin(ADMIN_NAME, ADMIN_PASSWORD));
    }

    /**
     * Register users, if they do not exist, and prints JWT authentication token.
     */
    private void registerUsersAndPrintToken() {
        registerAndPrintTokenForUser(USER_NAME_ONE, USER_EMAIL_ONE, USER_PASSWORD_ONE);
        registerAndPrintTokenForUser(USER_NAME_TWO, USER_EMAIL_TWO, USER_PASSWORD_TWO);
    }

    /**
     * Register user, if they do not exist, and prints JWT authentication token.
     */
    private void registerAndPrintTokenForUser(String userName, String userEmail, String userPassword) {
        userService.findByName(userName).ifPresentOrElse(
                userEntity -> logger.info("User already exists. Skipping registration."),
                () -> {
                    RegistrationRequestDto user = RegistrationRequestDto.builder()
                            .name(userName)
                            .email(userEmail)
                            .password(userPassword)
                            .build();
                    registrationService.registerUser(user);
                }
        );
        logger.info("Token for {}: {}", userName, authService.attemptLogin(userName, userPassword));
    }

    /**
     * Create orders and same them in repository.
     */
    private void createOrderEntities() {
        UserEntity userEntityOne = userService.findByName(USER_NAME_ONE).orElseThrow();
        UserEntity userEntityTwo = userService.findByName(USER_NAME_TWO).orElseThrow();

        List<OrderEntity> orderEntities = Arrays.asList(
                createOrderEntity("Delivered", "Food", "Apple", userEntityOne),
                createOrderEntity("Not delivered", "Book", "Shildt", userEntityOne),
                createOrderEntity("Not delivered", "Clothes", "T-shirts", userEntityTwo),
                createOrderEntity("Not delivered", "Game console", "Xbox", userEntityTwo),
                createOrderEntity("Delivered", "Phone", "Oneplus", userEntityTwo)
        );

        orderRepository.saveAll(orderEntities);
    }

    /**
     * Create orders entity.
     */
    private OrderEntity createOrderEntity(String status, String title, String description, UserEntity userEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(status);
        orderEntity.setTitle(title);
        orderEntity.setDescription(description);
        orderEntity.setUserEntity(userEntity);
        return orderEntity;
    }
}