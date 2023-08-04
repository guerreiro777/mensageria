package com.br.mensageria.consumer.service;

import com.br.mensageria.consumer.component.MessageReceiver;
import com.br.mensageria.consumer.domain.model.OrderModel;
import com.br.mensageria.consumer.domain.model.RoleModel;
import com.br.mensageria.consumer.domain.repository.OrderRepository;
import com.br.mensageria.consumer.domain.response.OrderResponse;
import com.br.mensageria.consumer.domain.response.mapper.OrderResponseMapper;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final RoleService roleService;
    @Autowired
    MessageReceiver receiver;

    public OrderService(OrderRepository orderRepository, final ClientService clientService, final RoleService roleService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.roleService = roleService;
    }

    public OrderResponse findById(final Long id) {
        Optional<OrderModel> orderModel = orderRepository.findById(id);
        if (orderModel.isEmpty()) {
            throw new MensageriaConsumerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Order"));
        }
        return OrderResponseMapper.INSTANCE.modelToResponse(orderModel.get());
    }

    public List<OrderResponse> findAll() {
        List<OrderModel> orders = orderRepository.findAll();
        List<OrderResponse> orderResponseList = orders.stream().map(orderModel -> OrderResponseMapper.INSTANCE.modelToResponse(orderModel))
                .collect(Collectors.toList());
        return orderResponseList;
    }

    public List<OrderResponse> filter(final Long id) {
        List<Optional<OrderModel>> leaders = orderRepository.findAll((Specification<OrderModel>)
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (id != null) {
                        Join<OrderModel, RoleModel> roleJoin = root.join("role");
                        predicates.add(criteriaBuilder.equal(roleJoin.get("id"), id));
                    }

                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        return leaders.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OrderResponseMapper.INSTANCE::modelToResponse)
                .collect(Collectors.toList());
    }

    public void messageError(String message) {
        //@TODO implement
    }

    public void message(String message) throws JsonProcessingException {

        OrderModel order = dataCast(message);
        List<OrderModel> listOrder = List.of();

        receiver.getMessages().forEach(
                data -> {
                    try {
                        OrderModel orderCompare = dataCast(data.getMessage());
                        if (orderCompare.getClient().getId().equals(order.getClient().getId())) {
                            listOrder.add(orderCompare);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        sendPackage(listOrder);
    }

    private OrderModel dataCast(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, OrderModel.class);
    }

    private void sendPackage(List<OrderModel> listOrder) {
        if(listOrder.size() == 1){
            log.info("Package will be shipped with {} product.", listOrder.size());
            //@TODO implement new rule
        } else if(listOrder.size() > 1){
            log.info("Package will be shipped with {} product.", listOrder.size());
            //@TODO implement new rule
        } else {
            log.info("Error. {}", listOrder.toArray());
            //@TODO implement new rule
        }
    }
}
