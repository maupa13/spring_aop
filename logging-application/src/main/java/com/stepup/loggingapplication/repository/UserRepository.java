package com.stepup.loggingapplication.repository;

import com.stepup.loggingapplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing user entities in the database.
 * This interface extends Spring Data JPA's JpaRepository, providing basic CRUD operations.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByNameIgnoreCase(String name);

    UserEntity findByEmailIgnoreCase(String email);
}
