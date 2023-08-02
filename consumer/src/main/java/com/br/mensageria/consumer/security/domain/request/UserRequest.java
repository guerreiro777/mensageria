package com.br.mensageria.consumer.security.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String email;
    private Set<String> role;
    private String password;
}