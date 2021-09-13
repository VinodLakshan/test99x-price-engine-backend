package com.project99x.priceengine.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ProductNotFoundException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final ZonedDateTime zonedDateTime = ZonedDateTime.now();

    public ProductNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
