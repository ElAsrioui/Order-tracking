package com.technical.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The EmailRequest class represents a request to email a customer regarding their order.
 * This class contains information about the order, customer email address, customer lastname, and the new status of the order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {

    private Long orderId;
    private String customerEmail;
    private String customerLastname;
    private String newStatus;

}
