package com.br.mensageria.producer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String zip_code;
    private String address;
    private String complement;
    private String reference_point;
    private String neighborhood;
    private String city;
    private String state;
}
