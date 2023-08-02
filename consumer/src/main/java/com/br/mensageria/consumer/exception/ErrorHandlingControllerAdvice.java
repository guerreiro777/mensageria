package com.br.mensageria.consumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MensageriaConsumerArgumentException.class)
    public final ResponseEntity<ExceptionResponse> argumentException(MensageriaConsumerArgumentException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MensageriaConsumerNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFoundException(MensageriaConsumerNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
