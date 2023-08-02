package com.br.mensageria.producer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressRequest {
    private String zip_code;
    private String address;
    private String complement;
    private String reference_point;
    private String neighborhood;
    private String city;
    private String state;
}
