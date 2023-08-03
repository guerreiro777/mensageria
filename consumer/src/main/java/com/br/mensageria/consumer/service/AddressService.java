package com.br.mensageria.consumer.service;

import com.br.mensageria.consumer.domain.model.AddressModel;
import com.br.mensageria.consumer.domain.repository.AddressRepository;
import com.br.mensageria.consumer.domain.request.AddressRequest;
import com.br.mensageria.consumer.exception.MensageriaConsumerArgumentException;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.consumer.util.GeneralUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

}
