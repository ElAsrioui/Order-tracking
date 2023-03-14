package com.technical.demo.services;

import com.technical.demo.dto.CustomerDto;
import com.technical.demo.entities.Customer;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.mappers.CustomerMapper;
import com.technical.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Creates a new customer with the provided CustomerDto.
     *
     * @param customerDto the CustomerDto containing the necessary information to create a new customer
     * @return the CustomerDto representing the created customer
     * @throws BadRequestException if the request is invalid or missing required information
     */
    public CustomerDto createCustomer(CustomerDto customerDto) {
        log.info("-----CustomerService--createCustomer-Method--Start-----");
        Customer customer;
        if (Objects.nonNull(customerDto) && Objects.nonNull(customerDto.getEmail())) {
            customer = customerRepository.save(CustomerMapper.INSTANCE.destinationToSource(customerDto));
        } else {
            log.error("<-----createCustomer--BAD-REQUEST------->");
            throw new BadRequestException("Bad Request");
        }
        log.info("-----CustomerService--createCustomer-Method--End-----");
        return CustomerMapper.INSTANCE.destinationToTarget(customer);
    }
}
