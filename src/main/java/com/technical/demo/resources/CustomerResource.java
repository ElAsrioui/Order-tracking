package com.technical.demo.resources;

import com.technical.demo.dto.CustomerDto;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerResource {

    private final CustomerService customerService;

    /**
     * Creates a new customer with the provided CustomerDto and returns a ResponseEntity containing the created customer.
     *
     * @param customerDto the CustomerDto containing the necessary information to create a new customer
     * @return a ResponseEntity containing the CustomerDto representing the created customer
     * @throws BadRequestException if the request is invalid or missing required information
     */
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        log.info("-----CustomerResource--createCustomer-Method-----");
        return ResponseEntity.ok().body(customerService.createCustomer(customerDto));
    }
}
