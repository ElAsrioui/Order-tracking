package com.technical.demo.dto;

import com.technical.demo.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object class for Order information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long customerId;
    private int totalOrderAmount;
    private OrderStatus status;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;

}
