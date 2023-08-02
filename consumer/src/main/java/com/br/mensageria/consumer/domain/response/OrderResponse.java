package com.br.mensageria.consumer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private ClientResponse client;
    private RoleResponse role;
    private Boolean receive_salary;
    private BigDecimal salary_amount;
    private String date_took_office;
}
