package com.technical.demo.resources;

import com.technical.demo.dto.OrderDto;
import com.technical.demo.dto.UpdateOrderStatusRequest;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.exception.NotFoundException;
import com.technical.demo.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderResource {

    private final OrderService orderService;


    /**
     * Creates a new order with the provided OrderDto and returns a ResponseEntity containing the created order.
     *
     * @param orderDto the OrderDto containing the necessary information to create a new order
     * @return a ResponseEntity containing the OrderDto representing the created order
     * @throws BadRequestException if the request is invalid or missing required information
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        log.info("-----OrderResource--createOrder-Method-----");
        return ResponseEntity.ok().body(orderService.createOrder(orderDto));
    }


    /**
     * Updates the status of an order with the provided orderId and UpdateOrderStatusRequest, and returns a ResponseEntity containing the updated order.
     *
     * @param orderId the ID of the order to update
     * @param request the UpdateOrderStatusRequest containing the necessary information to update the order status
     * @return a ResponseEntity containing the OrderDto representing the updated order
     * @throws NotFoundException if the order with the specified ID cannot be found
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestBody UpdateOrderStatusRequest request) {
        log.info("-----OrderResource--updateOrderStatus-Method-----");
        return ResponseEntity.ok().body(orderService.updateOrderStatus(orderId, request));
    }

}
