package com.br.mensageria.producer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MensageriaProducerArgumentException.class)
    public final ResponseEntity<ExceptionResponse> argumentException(MensageriaProducerArgumentException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MensageriaProducerNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFoundException(MensageriaProducerNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
