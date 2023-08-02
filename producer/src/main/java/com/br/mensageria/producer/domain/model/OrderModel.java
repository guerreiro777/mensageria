package com.br.mensageria.producer.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private ClientModel client;

    @Column(name = "product_value")
    private BigDecimal product_value;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "date_buyed")
    private String date_buyed;

    @Column(name = "amount")
    private Long amount;
}
