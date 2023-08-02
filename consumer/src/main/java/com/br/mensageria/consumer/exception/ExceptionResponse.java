package com.br.mensageria.consumer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
}
