package com.stepup.loggingapplication.service;

import com.stepup.loggingapplication.entity.UserEntity;
import com.stepup.loggingapplication.model.RegistrationRequestDto;
import com.stepup.loggingapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user registration operations in the application.
 * This class is annotated with Spring's @Service, making it a Spring service bean.
 * It is annotated with Lombok's {@code @RequiredArgsConstructor} for constructor injection.
 * It uses a UserRepository for database interactions and a PasswordEncoder for password hashing.
 *
 * @see org.springframework.stereotype.Service
 * @see com.stepup.loggingapplication.repository.UserRepository
 * @see PasswordEncoder
 * @see com.stepup.loggingapplication.entity.UserEntity
 * @see com.stepup.loggingapplication.model.RegistrationRequestDto
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    /**
     * UserRepository for database interactions.
     */
    private final UserRepository userRepository;

    /**
     * PasswordEncoder for password hashing.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with role using the provided RegistrationRequestDto.
     *
     * @param request The RegistrationRequestDto containing information about the user to be registered.
     */
    public void registerUser(RegistrationRequestDto request) {
        UserEntity newUser = new UserEntity();

        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setRole("ROLE_USER");
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
    }
}
