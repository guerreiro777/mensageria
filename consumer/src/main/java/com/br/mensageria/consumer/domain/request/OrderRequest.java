package com.br.mensageria.consumer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class OrderRequest {
    private Long client_id;
    private Long role_id;
    private Boolean receive_salary;
    private BigDecimal salary_amount;
    private String date_took_office;
}
