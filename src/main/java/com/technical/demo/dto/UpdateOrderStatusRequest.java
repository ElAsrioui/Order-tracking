package com.technical.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UpdateOrderStatusRequest class represents a request to update the status of an order.
 * This class contains information about the new status of the order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {

    private String newStatus;

}
