package com.br.mensageria.producer.component;

import com.br.mensageria.producer.domain.request.ClienteRequest;
import com.br.mensageria.producer.domain.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message, ClienteRequest obj) throws JsonProcessingException {
        System.out.println(message);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        rabbitTemplate.convertAndSend("messages-exchange", "message-key", message +
                ow.writeValueAsString(obj));
    }

    public void send(String message, OrderRequest obj) throws JsonProcessingException {
        System.out.println(message);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        rabbitTemplate.convertAndSend("messages-exchange", "message-key", message +
                ow.writeValueAsString(obj));
    }

}
