package com.stepup.loggingapplication.service;

import com.stepup.loggingapplication.entity.OrderEntity;
import com.stepup.loggingapplication.exception.NotFoundException;
import com.stepup.loggingapplication.model.OrderDto;
import com.stepup.loggingapplication.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing order-related operations in the application.
 * This class is annotated with Spring's @Service, making it a Spring service bean.
 * It is annotated with Lombok's {@code @RequiredArgsConstructor} for constructor injection.w
 * It uses a OrderRepository for database interactions.
 *
 * @see org.springframework.stereotype.Service
 * @see com.stepup.loggingapplication.repository.OrderRepository
 * @see com.stepup.loggingapplication.entity.OrderEntity
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    /**
     * OrderRepository for database interactions.
     */
    private final OrderRepository orderRepository;

    /**
     * Saves a new order using the provided orderDto.
     *
     * @param orderDto The orderRequest containing information about the order to be saved.
     * @return The saved OrderEntity.
     */
    public OrderEntity saveOrder(OrderDto orderDto) {
        OrderEntity orderEntityUpdated = new OrderEntity();

        orderEntityUpdated.setTitle(orderDto.getTitle());
        orderEntityUpdated.setDescription(orderDto.getDescription());

        return orderRepository.save(orderEntityUpdated);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all OrderEntity instances.
     */
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves the order by its ID from the database.
     *
     * @param id The ID of the order to retrieve.
     * @return An Optional containing the retrieved OrderEntity, or empty if not found.
     */
    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Update existing Order using the provided OrderDto.
     *
     * @param orderDto The OrderDto containing information about the order to be updated.
     * @return The saved OrderEntity.
     */
    public OrderEntity updateOrder(OrderDto orderDto) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderDto.getId());

        if (orderEntity.isPresent()) {
            OrderEntity orderEntityUpdated = new OrderEntity();

            orderEntityUpdated.setTitle(orderDto.getTitle());
            orderEntityUpdated.setDescription(orderDto.getStatus());
            orderEntityUpdated.setStatus(orderDto.getStatus());

            return orderRepository.save(orderEntityUpdated);
        } else {
            throw new NotFoundException("Order does not exist. order id: " + orderDto.getId());
        }
    }

    /**
     * Deletes an order by its ID from the database.
     *
     * @param id The ID of the order to delete.
     */
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
