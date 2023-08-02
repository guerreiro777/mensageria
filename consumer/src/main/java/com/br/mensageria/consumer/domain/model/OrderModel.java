package com.br.mensageria.consumer.domain.model;


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

    @OneToOne
    private RoleModel role;

    @Column(name = "receive_salary")
    private Boolean receive_salary;

    @Column(name = "salary_amount")
    private BigDecimal salary_amount;

    @Column(name = "date_took_office")
    private Date date_took_office;
}
