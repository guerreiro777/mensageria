package com.br.mensageria.consumer.controller;

import com.br.mensageria.consumer.domain.request.OrderRequest;
import com.br.mensageria.consumer.domain.response.OrderResponse;
import com.br.mensageria.consumer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create an order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody OrderRequest orderRequest) {
        log.info("Creating order with data: {}", orderRequest.toString());
        orderService.save(orderRequest, null);
        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "Updating an order")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody OrderRequest orderRequest, @PathVariable Long id) {
        log.info("Updating order with data: {}", orderRequest.toString());
        orderService.save(orderRequest, id);
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