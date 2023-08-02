package com.br.mensageria.producer.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_client")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "client_name")
    private String client_name;

    @Column(name = "birth_date")
    private Date birth_date;

    @Column(name = "gender")
    private String gender;

    @Column(name = "marital_status")
    private String marital_status;

    @Column(name = "spouse_name")
    private String spouse_name;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "academic_education")
    private String academic_education;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private BigInteger telephone;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "register_date")
    private Date register_date;

    @OneToOne
    private AddressModel address;
}
