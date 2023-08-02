package com.br.mensageria.producer.service;

import com.br.mensageria.producer.domain.model.AddressModel;
import com.br.mensageria.producer.domain.model.ClientModel;
import com.br.mensageria.producer.domain.repository.ClientRepository;
import com.br.mensageria.producer.domain.request.ClienteRequest;
import com.br.mensageria.producer.domain.response.ClientResponse;
import com.br.mensageria.producer.domain.response.mapper.ClientResponseMapper;
import com.br.mensageria.producer.exception.MensageriaProducerArgumentException;
import com.br.mensageria.producer.exception.MensageriaProducerNotFoundException;
import com.br.mensageria.producer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.producer.service.business.messages.ClientBusinessMessages;
import com.br.mensageria.producer.util.GeneralUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(final ClienteRequest clienteRequest, final Long client_id) {
        // General Validation
        validateInputs(clienteRequest, client_id);

        ClientModel clientModel = new ClientModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long address_id = null;

        // Check if create or update
        if (client_id == null) {
            clientModel.setRegister_date(new Date());
            clientModel.setActive(true);
        } else {
            ClientModel resultSet = clientRepository.findById(client_id).get();
            address_id = resultSet.getAddress().getId();
            clientModel.setId(client_id);
            clientModel.setRegister_date(resultSet.getRegister_date());
            clientModel.setActive(clienteRequest.getActive());
        }

        // Save Address
        final AddressModel addressModel = addressService.save(clienteRequest.getAddress(), address_id);

        // Save Client
        clientModel.setCpf(clienteRequest.getCpf());
        clientModel.setClient_name(clienteRequest.getClient_name());
        Date birthDate = null;
        try {
            birthDate = dateFormat.parse(clienteRequest.getBirth_date());
        } catch (ParseException e) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.DATE.getMessage("birth_date"));
        }
        clientModel.setBirth_date(birthDate);
        clientModel.setGender(clienteRequest.getGender());
        clientModel.setMarital_status(clienteRequest.getMarital_status());
        clientModel.setEmail(clienteRequest.getEmail());
        clientModel.setSpouse_name(clienteRequest.getSpouse_name());
        clientModel.setNationality(clienteRequest.getNationality());
        clientModel.setAcademic_education(clienteRequest.getAcademic_education());
        clientModel.setTelephone(clienteRequest.getTelephone());
        clientModel.setAddress(addressModel);
        if (!GeneralUtils.isNullOrEmpty(clienteRequest.getDeparture_date())) {
            Date departureDate = null;
            try {
                departureDate = dateFormat.parse(clienteRequest.getDeparture_date());
            } catch (ParseException e) {
                throw new MensageriaProducerArgumentException(GeneralBusinessMessages.DATE.getMessage("departure_date"));
            }
            clientModel.setDeparture_date(departureDate);
        }

        clientRepository.save(clientModel);
    }

    private void validateInputs(final ClienteRequest clienteRequest, final Long id) {
        generalValidate(clienteRequest);
        // Update
        if (id != null) {
            if (clientRepository.findById(id).isEmpty()) {
                throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Client"));
            }
            // Create
        } else {
            // CPF it must be unique
            if (clientRepository.findByCpf(clienteRequest.getCpf()).isPresent()) {
                throw new MensageriaProducerArgumentException(GeneralBusinessMessages.EXISTS_IN_DATABASE.getMessage("Cpf"));
            }
        }
    }

    private void generalValidate(final ClienteRequest clienteRequest) {
        // Validate Null Or Empty
        if (GeneralUtils.isNullOrEmpty(clienteRequest.getCpf())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("cpf"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getClient_name())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("client_name"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getBirth_date())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("birth_date"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getGender())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("gender"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getMarital_status())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("marital_status"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getSpouse_name())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("spouse_name"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getNationality())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("nationality"));
        } else if (GeneralUtils.isNullOrEmpty(clienteRequest.getAcademic_education())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("academic_education"));
        } else if (!GeneralUtils.isNumeric(clienteRequest.getTelephone())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NUMERIC.getMessage("telephone"));
        } else if (!GeneralUtils.isBoolean(clienteRequest.getActive())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.BOOLEAN.getMessage("active"));
        }

        // General Validates
        else if (!GeneralUtils.isCpf(clienteRequest.getCpf())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.GENERAL_INVALID_MESSAGE.getMessage("cpf"));
        } else if (!GeneralUtils.isNullOrEmpty(clienteRequest.getEmail()) && !GeneralUtils.isEmail(clienteRequest.getEmail())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.GENERAL_INVALID_MESSAGE.getMessage("email"));
        } else if (!clienteRequest.getActive() && GeneralUtils.isNullOrEmpty(clienteRequest.getDeparture_date())) {
            throw new MensageriaProducerArgumentException(ClientBusinessMessages.ACTIVE_FALSE.getMessage("departure_date"));
        }
    }

    public ClientModel findById(final Long id) {
        Optional<ClientModel> clientModel = clientRepository.findById(id);
        if (clientModel.isEmpty()) {
            throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Client"));
        }
        return clientModel.get();
    }

    public ClientResponse findById_Response(final Long id) {
        return ClientResponseMapper.INSTANCE.modelToResponse(this.findById(id));
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
}
