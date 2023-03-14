package com.technical.demo.services;

import com.technical.demo.dto.CustomerDto;
import com.technical.demo.entities.Customer;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CustomerServiceTest is a class that contains test cases for the CustomerService class
 */
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    /**
     * Sets up the mock objects for each test method
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the createCustomer method when all parameters are provided
     */
    @Test
    @DisplayName("Should create customer when all parameters are provided")
    public void testCreateCustomerSuccess() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("test@example.com");
        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());

        when(customerRepository.save(any())).thenReturn(customer);

        // Act
        CustomerDto result = customerService.createCustomer(customerDto);

        // Assert
        assertNotNull(result);
        assertEquals(customerDto.getEmail(), result.getEmail());
        verify(customerRepository, times(1)).save(any());
    }

    /**
     * Tests the createCustomer method when customerDto is null
     * Expects a BadRequestException to be thrown
     */
    @Test
    @DisplayName("Should throw BadRequestException when customerDto is null")
    public void testCreateCustomerNullInput() {
        // Arrange
        CustomerDto customerDto = null;

        // Act & Assert
        assertThrows(BadRequestException.class, () -> customerService.createCustomer(customerDto));
        verify(customerRepository, never()).save(any());
    }

    /**
     * Tests the createCustomer method when customerDto email is null
     * Expects a BadRequestException to be thrown
     */
    @Test
    @DisplayName("Should throw BadRequestException when customerDto.email is null")
    public void testCreateCustomerWithNullEmail() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();

        // Act & Assert
        assertThrows(BadRequestException.class, () -> customerService.createCustomer(customerDto));
        verify(customerRepository, never()).save(any());
    }
}

