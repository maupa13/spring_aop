package com.stepup.loggingapplication.repository;

import com.stepup.loggingapplication.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for managing order entities in the database.
 * This interface extends Spring Data JPA's JpaRepository, providing basic CRUD operations.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity getOrderEntityById(Long id);
}
