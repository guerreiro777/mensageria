package com.br.mensageria.consumer.controller;

import com.br.mensageria.consumer.domain.request.RoleRequest;
import com.br.mensageria.consumer.domain.response.RoleResponse;
import com.br.mensageria.consumer.service.RoleService;
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
@RequestMapping(value = "/api/v1/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create an role")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody RoleRequest roleRequest) {
        log.info("Creating role with data: {}", roleRequest.toString());
        roleService.save(roleRequest, null);
        return ResponseEntity.created(null).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updating an role")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody RoleRequest roleRequest, @PathVariable Long id) {
        log.info("Updating role with data: {}", roleRequest.toString());
        roleService.save(roleRequest, id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Geting all roles")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResponse> findAll() {
        log.info("Geting all roles");
        return roleService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Searching an role with ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse findById(@PathVariable Long id) {
        log.info("Searching role with ID: ", id);
        return roleService.findById_Response(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an role by ID")
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        log.info("Delete Role with ID: {}", id);
        roleService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Filter a role through parameters")
    @PostMapping(value = "/filter")
    @ResponseStatus(HttpStatus.CREATED)
    public List<RoleResponse> find(@RequestBody RoleRequest roleRequest) {
        log.info("Filter a role through parameters: {}", roleRequest.toString());
        return roleService.filter(roleRequest);
    }

}
