package com.technical.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be thrown when a requested resource is not found.
 * This exception is mapped to a HTTP response with a 404 NOT FOUND status code.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code NotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
