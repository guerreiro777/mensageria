package com.br.mensageria.producer.service;

import com.br.mensageria.producer.domain.model.AddressModel;
import com.br.mensageria.producer.domain.repository.AddressRepository;
import com.br.mensageria.producer.domain.request.AddressRequest;
import com.br.mensageria.producer.exception.MensageriaProducerArgumentException;
import com.br.mensageria.producer.exception.MensageriaProducerNotFoundException;
import com.br.mensageria.producer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.producer.util.GeneralUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressModel save(final AddressRequest addressRequest, final Long address_id) {
        validateInputs(addressRequest, address_id);

        AddressModel addressModel = new AddressModel();

        if (address_id != null) {
            addressModel.setId(address_id);
        }

        addressModel.setZip_code(addressRequest.getZip_code());
        addressModel.setAddress(addressRequest.getAddress());
        addressModel.setComplement(addressRequest.getComplement());
        addressModel.setReference_point(addressRequest.getReference_point());
        addressModel.setNeighborhood(addressRequest.getNeighborhood());
        addressModel.setCity(addressRequest.getCity());
        addressModel.setState(addressRequest.getState());

        return addressRepository.save(addressModel);
    }

    private void validateInputs(final AddressRequest addressRequest, final Long address_id) {
        if (address_id != null) {
            if (addressRepository.findById(address_id).isEmpty()) {
                throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Address"));
            }
        }

        if (addressRequest.getZip_code().length() != 8) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.ZIP_CODE.getMessage("zip_code"));
        }
        else if (GeneralUtils.isNullOrEmpty(addressRequest.getAddress())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("address"));
        }
        else if (GeneralUtils.isNullOrEmpty(addressRequest.getComplement())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("complement"));
        }
        else if (GeneralUtils.isNullOrEmpty(addressRequest.getNeighborhood())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("neighborhood"));
        }
        else if (GeneralUtils.isNullOrEmpty(addressRequest.getCity())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("city"));
        }
        else if (GeneralUtils.isNullOrEmpty(addressRequest.getState())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("state"));
        }
    }

    public void deleteById(final Long address_id) {
        addressRepository.deleteById(address_id);
    }
}
