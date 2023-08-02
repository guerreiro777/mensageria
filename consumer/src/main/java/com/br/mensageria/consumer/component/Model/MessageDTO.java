package com.br.mensageria.consumer.component.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private LocalDateTime timestamp;
    private String message;

    public MessageDTO(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}
