package com.technical.demo.resources;

import com.technical.demo.dto.OrderDto;
import com.technical.demo.dto.UpdateOrderStatusRequest;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.exception.NotFoundException;
import com.technical.demo.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * OrderResourceTest is a class that contains test cases for the OrderResource class
 */
public class OrderResourceTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderResource orderResource;

    /**
     * Sets up the mock objects for each test method
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that an order can be created successfully with all parameters provided.
     */
    @Test
    @DisplayName("Should create order when all parameters are provided")
    public void testCreateOrderSuccess() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        when(orderService.createOrder(orderDto)).thenReturn(orderDto);

        // Act
        ResponseEntity<OrderDto> response = orderResource.createOrder(orderDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
    }

    /**
     * Tests that an order's status can be updated successfully with all parameters provided.
     */
    @Test
    @DisplayName("Should update order status when all parameters are provided")
    public void testUpdateOrderStatusSuccess() {
        // Arrange
        Long orderId = 1L;
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        OrderDto orderDto = new OrderDto();
        when(orderService.updateOrderStatus(orderId, request)).thenReturn(orderDto);

        // Act
        ResponseEntity<OrderDto> response = orderResource.updateOrderStatus(orderId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
    }

    /**
     * Tests that a NotFoundException is thrown when no order is found.
     */
    @Test
    @DisplayName("Should throw NotFoundException when no order is found")
    public void testUpdateOrderStatusWithNonExistingOrder() {
        // Arrange
        Long orderId = 1L;
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        when(orderService.updateOrderStatus(orderId, request)).thenThrow(new NotFoundException("Order not found"));

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> orderResource.updateOrderStatus(orderId, request));
    }

    /**
     * Tests that a BadRequestException is thrown when an orderDto is null.
     */
    @Test
    @DisplayName("Should throw BadRequestException when orderDto is null")
    public void testCreateOrderWithNullInput() {
        // Arrange
        OrderDto orderDto = null;
        when(orderService.createOrder(orderDto)).thenThrow(new BadRequestException("Invalid request"));

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> orderResource.createOrder(orderDto));
    }

}
