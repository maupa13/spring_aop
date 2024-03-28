package com.stepup.loggingapplication.controller;

import com.stepup.loggingapplication.entity.OrderEntity;
import com.stepup.loggingapplication.exception.NotFoundException;
import com.stepup.loggingapplication.model.OrderDto;
import com.stepup.loggingapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to order entities.
 * Provides endpoints for retrieving, creating, updating, and deleting orders.
 * Base URL is "/orders".
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieve a list of all orders.
     *
     * @return ResponseEntity with a list of OrderEntity objects and HTTP status OK (200).
     */
    @GetMapping("/")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Retrieve a specific order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return ResponseEntity with the OrderEntity object and HTTP status OK (200).
     * @throws com.stepup.loggingapplication.exception.NotFoundException if the order with the specified ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderEntity orderEntity = orderService.getOrderById(id)
                .orElseThrow(() -> new NotFoundException("order not found. order id: " + id));

        return ResponseEntity.ok(orderEntity);
    }

    /**
     * Create a new order.
     * Requires the 'ADMIN' role for access.
     *
     * @param orderDto The request containing information about the new order.
     * @return ResponseEntity with the created OrderEntity object and HTTP status CREATED (201).
     */
    @PostMapping("/")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = orderService.saveOrder(orderDto);
        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }

    /**
     * Update an existing order.
     * Requires the 'ADMIN' role for access.
     *
     * @param orderDto The request containing updated information about the order.
     * @return ResponseEntity with the updated OrderEntity object and HTTP status OK (200).
     */
    @PutMapping("/")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = orderService.updateOrder(orderDto);
        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }

    /**
     * Delete an order by its ID.
     * Requires the 'ADMIN' role for access.
     *
     * @param id The ID of the order to delete.
     * @return ResponseEntity with the deleted OrderEntity object and HTTP status OK (200).
     * @throws com.stepup.loggingapplication.exception.NotFoundException if the order with the specified ID is not found.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        OrderEntity orderEntity = orderService.getOrderById(id)
                .orElseThrow(() -> new NotFoundException("Order not found. order id: " + id));

        orderService.deleteOrderById(id);

        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }
}
