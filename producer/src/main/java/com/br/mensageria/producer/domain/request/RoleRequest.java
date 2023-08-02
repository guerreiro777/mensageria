package com.br.mensageria.producer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoleRequest {
    private String role_name;
    private String description;
    private Boolean is_ecclesiastical_function;
    private Integer hierarchical_order;
}
