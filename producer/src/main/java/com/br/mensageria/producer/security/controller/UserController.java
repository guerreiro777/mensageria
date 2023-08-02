package com.br.mensageria.producer.security.controller;

import com.br.mensageria.producer.security.domain.request.UserRequest;
import com.br.mensageria.producer.security.domain.response.UserResponse;
import com.br.mensageria.producer.security.service.UserService;
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
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create an user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody UserRequest userRequest) {
        log.info("Creating user with data: {}", userRequest.toString());
        userService.save(userRequest, null);
        return ResponseEntity.created(null).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an user")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        log.info("Updating user with data: {}", userRequest.toString());
        userService.save(userRequest, id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Geting all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAll() {
        log.info("Geting all users");
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Searching an user with ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findById(@PathVariable Long id) {
        log.info("Searching user with ID: ", id);
        return userService.findById_Response(id);
    }

}
