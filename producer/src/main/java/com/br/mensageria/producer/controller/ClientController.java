package com.br.mensageria.producer.controller;

import com.br.mensageria.producer.component.MessageSender;
import com.br.mensageria.producer.domain.request.ClienteRequest;
import com.br.mensageria.producer.domain.response.ClientResponse;
import com.br.mensageria.producer.service.ClientService;
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
@RequestMapping(value = "/api/v1/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final ClientService clientService;
    private final MessageSender messageSender;

    public ClientController(final ClientService clientService, MessageSender messageSender) {
        this.clientService = clientService;
        this.messageSender = messageSender;
    }

    @Operation(summary = "Create an client")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody ClienteRequest clientRequest) throws JsonProcessingException {
        log.info("Creating client with data: {}", clientRequest.toString());
        try {
            clientService.save(clientRequest, null);
            this.messageSender.send("[Client added]", clientRequest);
        } catch (Exception e) {
            this.messageSender.send("[Client not added][" + e.getMessage() + "]", clientRequest);
        }
        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "Update an client")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody ClienteRequest clientRequest, @PathVariable Long id) throws JsonProcessingException {
        log.info("Updating client with data: {}", clientRequest.toString());
        try {
            clientService.save(clientRequest, id);
            this.messageSender.send("[Client changed]", clientRequest);
        } catch (Exception e) {
            this.messageSender.send("[Client not added][" + e.getMessage() + "]", clientRequest);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Geting all clients")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponse> findAll() {
        log.info("Geting all clients");
        return clientService.findAll();
    }

    @Operation(summary = "Searching an client with ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponse findById(@PathVariable Long id) {
        log.info("Searching client with ID: {}", id);
        return clientService.findById_Response(id);
    }

    @Operation(summary = "Filter a client through parameters")
    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponse> filter(@RequestBody ClienteRequest clientRequest) {
        log.info("Filter a client through parameters: {}", clientRequest.toString());
        return clientService.filter(clientRequest);
    }


}
