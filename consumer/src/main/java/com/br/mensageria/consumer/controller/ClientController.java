package com.br.mensageria.consumer.controller;

import com.br.mensageria.consumer.domain.request.ClienteRequest;
import com.br.mensageria.consumer.domain.response.ClientResponse;
import com.br.mensageria.consumer.service.ClientService;
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
@RequestMapping(value = "/api/v1/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final ClientService clientService;

    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Create an client")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody ClienteRequest clientRequest) {
        log.info("Creating client with data: {}", clientRequest.toString());
        clientService.save(clientRequest, null);
        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "Update an client")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody ClienteRequest clientRequest, @PathVariable Long id) {
        log.info("Updating client with data: {}", clientRequest.toString());
        clientService.save(clientRequest, id);
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
        log.info("Searching client with ID: ", id);
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