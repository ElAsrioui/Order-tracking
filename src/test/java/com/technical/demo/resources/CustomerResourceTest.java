package com.technical.demo.resources;

import com.technical.demo.dto.CustomerDto;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

/**
 * CustomerResourceTest is a class that contains test cases for the CustomerResource class
 */
public class CustomerResourceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerResource customerResource;

    /**
     * Sets up the mock objects for each test method
     */
    @BeforeEach
    public void initMocks() {
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
        customerDto.setLastname("LASTNAME");
        customerDto.setEmail("test@example.com");

        when(customerService.createCustomer(customerDto)).thenReturn(customerDto);

        // Act
        ResponseEntity<CustomerDto> response = customerResource.createCustomer(customerDto);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(customerDto, response.getBody());
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

        when(customerService.createCustomer(customerDto)).thenThrow(new BadRequestException("Bad Request"));

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> customerResource.createCustomer(customerDto));
    }
}
