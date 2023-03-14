package com.technical.demo.services;

import com.technical.demo.dto.EmailRequest;
import com.technical.demo.dto.OrderDto;
import com.technical.demo.dto.UpdateOrderStatusRequest;
import com.technical.demo.entities.Order;
import com.technical.demo.enums.OrderStatus;
import com.technical.demo.exception.BadRequestException;
import com.technical.demo.exception.NotFoundException;
import com.technical.demo.mappers.OrderMapper;
import com.technical.demo.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final EmailSenderService emailSenderService;

    /**
     * Creates an order using the provided OrderDto.
     *
     * @param orderDto the OrderDto containing the information for the new order
     * @return the OrderDto for the created order
     * @throws BadRequestException if the provided orderDto is null or the customerId is null
     */
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("-----OrderService--createOrder-Method--Start-----");
        Order order;
        if (Objects.nonNull(orderDto) && Objects.nonNull(orderDto.getCustomerId())) {
            order = OrderMapper.INSTANCE.destinationToSource(orderDto);
            order.setStatus(OrderStatus.PLACED);
            orderRepository.save(order);
        } else {
            throw new BadRequestException("Bad Request");
        }
        log.info("-----OrderService--createOrder-Method--End-----");
        return OrderMapper.INSTANCE.destinationToTarget(order);
    }

    /**
     * Updates the status of an order with the provided orderId using the UpdateOrderStatusRequest.
     *
     * @param orderId the ID of the order to update
     * @param request the UpdateOrderStatusRequest containing the new status for the order
     * @return the OrderDto for the updated order
     * @throws BadRequestException if the UpdateOrderStatusRequest is null or the newStatus value is not valid
     * @throws NotFoundException   if the order with the provided orderId is not found in the repository
     */
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        log.info("-----OrderService--updateOrderStatus-Method--Start-----");
        if (Objects.isNull(request)) {
            log.error("<-----updateOrderStatus--BAD-REQUEST------->");
            throw new BadRequestException("Bad request");
        }
        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found")));
        Order order;
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            if (EnumUtils.isValidEnum(OrderStatus.class, request.getNewStatus())) {
                log.info("Updating order status for order ID: {}", orderId);
                order.setStatus(OrderStatus.valueOf(request.getNewStatus()));
                orderRepository.save(order);
            } else {
                log.error("<-----updateOrderStatus--BAD-REQUEST------->");
                throw new BadRequestException("Please enter a correct status value");
            }
            EmailRequest emailRequest = EmailRequest.builder().orderId(order.getId()).customerEmail(order.getCustomer().getEmail()).customerLastname(order.getCustomer().getLastname()).newStatus(String.valueOf(order.getStatus())).build();
            emailSenderService.sendNotification(emailRequest);
        } else {
            log.error("<-----updateOrderStatus--BAD-REQUEST------->");
            throw new BadRequestException("Bad request");
        }
        log.info("Order status updated successfully for order ID: {}", orderId);
        log.info("-----OrderService--updateOrderStatus-Method--End-----");
        return OrderMapper.INSTANCE.destinationToTarget(order);
    }

}
