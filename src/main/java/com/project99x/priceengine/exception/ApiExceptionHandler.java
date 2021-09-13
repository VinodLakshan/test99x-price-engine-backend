package com.project99x.priceengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = DBException.class)
    public ResponseEntity<Object> handleDBException(DBException e){
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                e.getHttpStatus(),
                e.getZonedDateTime()
        );
        return new ResponseEntity<>(exceptionDetails, e.getHttpStatus());
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e){
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                e.getHttpStatus(),
                e.getZonedDateTime()
        );
        return new ResponseEntity<>(exceptionDetails, e.getHttpStatus());
    }
}
