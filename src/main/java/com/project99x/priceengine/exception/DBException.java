package com.project99x.priceengine.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class DBException extends RuntimeException {

    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final ZonedDateTime zonedDateTime = ZonedDateTime.now();

    public DBException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
