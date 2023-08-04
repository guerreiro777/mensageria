package com.br.mensageria.producer.service;

import com.br.mensageria.producer.domain.model.OrderModel;
import com.br.mensageria.producer.domain.model.ClientModel;
import com.br.mensageria.producer.domain.model.RoleModel;
import com.br.mensageria.producer.domain.repository.OrderRepository;
import com.br.mensageria.producer.domain.request.OrderRequest;
import com.br.mensageria.producer.domain.response.OrderResponse;
import com.br.mensageria.producer.domain.response.mapper.OrderResponseMapper;
import com.br.mensageria.producer.exception.MensageriaProducerArgumentException;
import com.br.mensageria.producer.exception.MensageriaProducerNotFoundException;
import com.br.mensageria.producer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.producer.util.GeneralUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final RoleService roleService;

    public OrderService(OrderRepository orderRepository, final ClientService clientService, final RoleService roleService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.roleService = roleService;
    }

    @Transactional
    public OrderModel save(final OrderRequest orderRequest, final Long order_id) throws ParseException {
        validateInputs(orderRequest);

        OrderModel orderModel = new OrderModel();

        if (order_id != null) {
            if (orderRepository.findById(order_id).isEmpty()) {
                throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Order"));
            }
            orderModel.setId(order_id);
        }

        ClientModel clientModel = clientService.findById(orderRequest.getClient_id());

        orderModel.setClient(clientModel);
        orderModel.setProduct_value(orderRequest.getProduct_value());
        orderModel.setVolume(orderRequest.getVolume());
        orderModel.setWeight(orderRequest.getWeight());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        orderModel.setDate_buyed(formatter.parse(orderRequest.getDate_buyed()));
        orderModel.setAmount(orderRequest.getAmount());

        return orderRepository.save(orderModel);
    }

    private void validateInputs(final OrderRequest orderRequest) {
        if (!GeneralUtils.isNumeric(Math.toIntExact(orderRequest.getClient_id()))) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NUMERIC.getMessage("client_id"));
        }
        else if (orderRequest.getProduct_value() == null || orderRequest.getVolume() == null) {
            throw new MensageriaProducerArgumentException("if field is required");
        }
    }


    public OrderResponse findById(final Long id) {
        Optional<OrderModel> orderModel = orderRepository.findById(id);
        if (orderModel.isEmpty()) {
            throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Order"));
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
}
