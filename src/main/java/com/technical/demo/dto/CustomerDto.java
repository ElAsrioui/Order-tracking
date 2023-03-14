package com.technical.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object class for Customer information.
 * The email field is annotated with @NotNull to indicate that it cannot be null.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private String firstname;
    private String lastname;
    @NotNull
    private String email;
}
