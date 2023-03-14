package com.technical.demo.services;

import com.technical.demo.dto.EmailRequest;
import com.technical.demo.dto.OrderDto;
import com.technical.demo.dto.UpdateOrderStatusRequest;
import com.technical.demo.entities.Customer;
import com.technical.demo.entities.Order;
import com.technical.demo.enums.OrderStatus;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.exception.NotFoundException;
import com.technical.demo.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * OrderServiceTest is a class that contains test cases for the OrderService class
 */
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmailSenderService emailSenderService;

    /**
     * Sets up the mock objects for each test method
     */
    @BeforeEach
    public void setUp() {
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
        orderDto.setCustomerId(1L);
        Customer customer = new Customer();
        customer.setId(1L);
        Order order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        OrderDto result = orderService.createOrder(orderDto);

        // Assert
        assertEquals(result.getStatus(), order.getStatus());
    }

    /**
     * Tests that a BadRequestException is thrown when an orderDto is null.
     */
    @Test
    @DisplayName("Should throw BadRequestException when orderDto is null")
    public void testCreateOrderWithNullInput() {
        // Arrange
        OrderDto orderDto = null;

        // Act & Assert
        assertThrows(BadRequestException.class, () -> orderService.createOrder(orderDto));
        verify(orderRepository, never()).save(any());
    }

    /**
     * Tests that a BadRequestException is thrown when an orderDto customerId is null.
     */
    @Test
    @DisplayName("Should throw BadRequestException when orderDto.customerId is null")
    public void testCreateOrderWithNullCustomerId() {
        // Arrange
        OrderDto orderDto = new OrderDto();

        // Act & Assert
        assertThrows(BadRequestException.class, () -> orderService.createOrder(orderDto));
        verify(orderRepository, never()).save(any());
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
        request.setNewStatus(OrderStatus.CONFIRMED.name());
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCustomer(new Customer());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, request);

        // Assert
        verify(orderRepository, times(1)).save(order);
        verify(emailSenderService, times(1)).sendNotification(any(EmailRequest.class));
        assertEquals(OrderStatus.CONFIRMED, updatedOrder.getStatus());
    }

    /**
     * Tests that a BadRequestException is thrown when an updateOrderStatusRequest is null.
     */
    @Test
    @DisplayName("Should throw BadRequestException when updateOrderStatusRequest is null")
    public void testUpdateOrderStatusWithNullInput() {
        // Arrange
        Long orderId = 1L;
        UpdateOrderStatusRequest request = null;

        // Act & Assert
        assertThrows(BadRequestException.class, () -> orderService.updateOrderStatus(orderId, request));
        verify(orderRepository, never()).save(any());
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

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateOrderStatus(orderId, request));
        verify(orderRepository, never()).save(any());
    }


    /**
     * Tests that a BadRequestException is thrown when UpdateOrderStatusRequest status is invalid.
     */
    @Test
    @DisplayName("Should throw BadRequestException when status is invalid")
    public void testUpdateOrderStatusWithInvalidStatus() {
        // Arrange
        Long orderId = 1L;
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setNewStatus("INVALID_STATUS");
        Order order = new Order();
        order.setCustomer(new Customer());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> orderService.updateOrderStatus(orderId, request));
    }

}

