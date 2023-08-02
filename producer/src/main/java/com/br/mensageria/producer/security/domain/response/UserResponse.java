package com.br.mensageria.producer.security.domain.response;

import com.br.mensageria.producer.security.domain.model.SystemRolesModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private Set<SystemRolesModel> roles;
}