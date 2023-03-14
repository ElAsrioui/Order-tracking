package com.technical.demo.mappers;

import com.technical.demo.dto.OrderDto;
import com.technical.demo.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * A mapper interface for mapping between Order and OrderDto objects.
 */
@Mapper
public interface OrderMapper {

    /**
     * INSTANCE field used for accessing the OrderMapper implementation.
     */
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Maps an Order object to an OrderDto object.
     *
     * @param target the Order object to map from
     * @return the mapped OrderDto object
     */
    @Mappings({
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "totalOrderAmount", target = "totalOrderAmount"),
            @Mapping(source = "shippingDetails.shippingAddress", target = "shippingAddress"),
            @Mapping(source = "shippingDetails.shippingCity", target = "shippingCity"),
            @Mapping(source = "shippingDetails.shippingCountry", target = "shippingCountry")
    })
    OrderDto destinationToTarget(Order target);

    /**
     * Maps an OrderDto object to an Order object.
     *
     * @param source the OrderDto object to map from
     * @return the mapped Order object
     */
    @Mappings({
            @Mapping(source = "customerId", target = "customer.id"),
            @Mapping(source = "totalOrderAmount", target = "totalOrderAmount"),
            @Mapping(source = "shippingAddress", target = "shippingDetails.shippingAddress"),
            @Mapping(source = "shippingCity", target = "shippingDetails.shippingCity"),
            @Mapping(source = "shippingCountry", target = "shippingDetails.shippingCountry")
    })
    Order destinationToSource(OrderDto source);

}
