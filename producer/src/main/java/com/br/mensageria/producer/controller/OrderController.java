package com.br.mensageria.producer.controller;

import com.br.mensageria.producer.component.MessageSender;
import com.br.mensageria.producer.domain.model.OrderModel;
import com.br.mensageria.producer.domain.request.OrderRequest;
import com.br.mensageria.producer.domain.response.OrderResponse;
import com.br.mensageria.producer.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class OrderController {

    private final OrderService orderService;
    private final MessageSender messageSender;

    public OrderController(OrderService orderService, MessageSender messageSender) {
        this.orderService = orderService;
        this.messageSender = messageSender;
    }

    @Operation(summary = "Create an order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        log.info("Creating order with data: {}", orderRequest.toString());
        try {
            OrderModel model = orderService.save(orderRequest, null);
            this.messageSender.send("[Order purchase]", model);
        } catch (Exception e) {
            this.messageSender.send("[Order NOT purchase][" + e.getMessage() + "]", orderRequest);
        }
        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "Updating an order")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody OrderRequest orderRequest, @PathVariable Long id) throws JsonProcessingException {
        log.info("Updating order with data: {}", orderRequest.toString());
        try {
            orderService.save(orderRequest, id);
            this.messageSender.send("[Order changed purchase]", orderRequest);
        } catch (Exception e) {
            this.messageSender.send("[Error Order changed purchase][" + e.getMessage() + "]", orderRequest);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Geting all orders")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAll() {
        log.info("Geting all orders");
        return orderService.findAll();
    }

    @Operation(summary = "Searching an order with ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findById(@PathVariable Long id) {
        log.info("Searching order with ID: {}", id);
        return orderService.findById(id);
    }

    @Operation(summary = "Searching order through parameters")
    @GetMapping("/filter/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> filter(@PathVariable Long id) {
        log.info("Searching order through parameters: {}", id);
        return orderService.filter(id);
    }
}
