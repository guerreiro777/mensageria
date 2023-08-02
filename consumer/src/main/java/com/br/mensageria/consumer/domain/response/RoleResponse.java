package com.br.mensageria.consumer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Long id;
    private String role_name;
    private String description;
    private Boolean is_ecclesiastical_function;
    private Integer hierarchical_order;
}
