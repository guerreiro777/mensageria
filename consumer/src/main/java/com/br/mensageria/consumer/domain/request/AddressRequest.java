package com.br.mensageria.consumer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

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
