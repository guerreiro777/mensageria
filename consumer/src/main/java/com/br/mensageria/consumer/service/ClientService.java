package com.br.mensageria.consumer.service;

import com.br.mensageria.consumer.domain.model.AddressModel;
import com.br.mensageria.consumer.domain.model.ClientModel;
import com.br.mensageria.consumer.domain.repository.ClientRepository;
import com.br.mensageria.consumer.domain.request.ClienteRequest;
import com.br.mensageria.consumer.domain.response.ClientResponse;
import com.br.mensageria.consumer.domain.response.mapper.ClientResponseMapper;
import com.br.mensageria.consumer.exception.MensageriaConsumerArgumentException;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.consumer.service.business.messages.ClientBusinessMessages;
import com.br.mensageria.consumer.util.GeneralUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final AddressService addressService;

    private final EmailService emailService;

    public ClientService(final ClientRepository clientRepository,
                         final AddressService addressService,
                         final EmailService emailService
    ) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
        this.emailService = emailService;
    }

    public ClientModel findById(final Long id) {
        Optional<ClientModel> clientModel = clientRepository.findById(id);
        if (clientModel.isEmpty()) {
            throw new MensageriaConsumerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Client"));
        }
        return clientModel.get();
    }

    public List<ClientResponse> findAll() {
        List<ClientModel> clients = clientRepository.findAll();
        List<ClientResponse> clientResponseList = clients.stream().map(
                        clientModel -> ClientResponseMapper.INSTANCE.modelToResponse(clientModel))
                .collect(Collectors.toList());
        return clientResponseList;
    }

    public List<ClientResponse> filter(final ClienteRequest clienteRequest) {
        List<Optional<ClientModel>> clients = clientRepository.findAll((Specification<ClientModel>)
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!GeneralUtils.isNullOrEmpty(clienteRequest.getClient_name())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("client_name")), "%" +
                                        clienteRequest.getClient_name().toUpperCase() + "%")));
                    }
                    if (!GeneralUtils.isNullOrEmpty(clienteRequest.getCpf())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cpf"),
                                clienteRequest.getCpf())));
                    }
                    if (!GeneralUtils.isNullOrEmpty(clienteRequest.getEmail())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("email")), "%" +
                                        clienteRequest.getEmail().toUpperCase() + "%")));
                    }
                    if (!GeneralUtils.isNullOrEmpty(clienteRequest.getGender())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("gender"),
                                clienteRequest.getGender())));
                    }
                    if (!GeneralUtils.isNullOrEmpty(clienteRequest.getBirth_date())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("birth_date"),
                                clienteRequest.getBirth_date())));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                });

        return clients.stream()
                .map(clientModel -> ClientResponseMapper.INSTANCE.modelToResponse(clientModel.get()))
                .sorted(Comparator.comparing(ClientResponse::getClient_name))
                .collect(Collectors.toList());

    }

    public void messageError(String message) {
        //@TODO implement
    }

    public void message(String message) {
        //@TODO implement
    }
}
