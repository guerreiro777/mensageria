package com.br.mensageria.producer.domain.response;

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
    private Long client_id;
    private BigDecimal product_value;
    private BigDecimal volume;
    private BigDecimal weight;
    private String date_buyed;
    private Long amount;
}
