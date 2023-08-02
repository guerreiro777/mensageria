package com.br.mensageria.producer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    //Client
    private Long id;
    private String cpf;
    private String client_name;
    private String birth_date;
    private String gender;
    private String marital_status;
    private String spouse_name;
    private String nationality;
    private String academic_education;
    private String email;
    private BigInteger telephone;
    private String departure_date;
    private Boolean active;

    //Address
    private AddressResponse address;
}
