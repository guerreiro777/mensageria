package com.br.mensageria.producer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class OrderRequest {
    private Long id;
    private Long client_id;
    private BigDecimal product_value;
    private BigDecimal volume;
    private BigDecimal weight;
    private String date_buyed;
    private Long amount;
}
