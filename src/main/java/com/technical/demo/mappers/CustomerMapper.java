package com.technical.demo.mappers;

import com.technical.demo.dto.CustomerDto;
import com.technical.demo.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * A mapper interface for mapping between Customer and CustomerDto objects.
 */
@Mapper
public interface CustomerMapper {

    /**
     * INSTANCE field used for accessing the CustomerMapper implementation.
     */
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * Maps a CustomerDto object to a Customer object.
     *
     * @param source the CustomerDto object to map from
     * @return the mapped Customer object
     */
    Customer destinationToSource(CustomerDto source);

    /**
     * Maps a Customer object to a CustomerDto object.
     *
     * @param target the Customer object to map from
     * @return the mapped CustomerDto object
     */
    CustomerDto destinationToTarget(Customer target);
}
