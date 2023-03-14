package com.technical.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the request sent by the client is not valid or malformed,
 * This exception is mapped to a HTTP response with a 400 Bad Request status code.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a new BadRequestException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}