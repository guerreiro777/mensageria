package com.br.mensageria.consumer.controller;

import com.br.mensageria.consumer.component.MessageReceiver;
import com.br.mensageria.consumer.component.Model.MessageDTO;
import com.br.mensageria.consumer.domain.request.OrderRequest;
import com.br.mensageria.consumer.domain.response.OrderResponse;
import com.br.mensageria.consumer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class    OrderController {

    private final OrderService orderService;
    private final MessageReceiver receiver;

    public OrderController(OrderService orderService, MessageReceiver receiver) {
        this.orderService = orderService;
        this.receiver = receiver;
    }

    @GetMapping("/message")
    public List<MessageDTO> getMessages() {
        return receiver.getMessages();
    }
}
