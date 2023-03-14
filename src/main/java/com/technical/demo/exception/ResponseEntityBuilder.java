package com.technical.demo.exception;

import org.springframework.http.ResponseEntity;

/**
 * A class for building a ResponseEntity object with an ApiError object as the response body.
 */
class ResponseEntityBuilder {

    /**
     * Builds a ResponseEntity object with the provided ApiError object as the response body and the HTTP status
     * code specified in the ApiError object.
     *
     * @param apiError the ApiError object to use as the response body
     * @return a ResponseEntity object with the ApiError object as the response body and the HTTP status code
     * specified in the ApiError object
     */
    public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
