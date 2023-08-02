package com.br.mensageria.consumer.service;

import com.br.mensageria.consumer.domain.model.OrderModel;
import com.br.mensageria.consumer.domain.model.ClientModel;
import com.br.mensageria.consumer.domain.model.RoleModel;
import com.br.mensageria.consumer.domain.repository.OrderRepository;
import com.br.mensageria.consumer.domain.request.OrderRequest;
import com.br.mensageria.consumer.domain.response.OrderResponse;
import com.br.mensageria.consumer.domain.response.mapper.OrderResponseMapper;
import com.br.mensageria.consumer.exception.MensageriaConsumerArgumentException;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.consumer.util.GeneralUtils;
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
    public void save(final OrderRequest orderRequest, final Long order_id) {
        // General Validation
        validateInputs(orderRequest);

        OrderModel orderModel = new OrderModel();

        // Check if create or update
        if (order_id != null) {
            if (orderRepository.findById(order_id).isEmpty()) {
                throw new MensageriaConsumerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Order"));
            }
            orderModel.setId(order_id);
        }

        ClientModel clientModel = clientService.findById(orderRequest.getClient_id());
        RoleModel roleModel = roleService.findById(orderRequest.getRole_id());

        orderModel.setClient(clientModel);
        orderModel.setRole(roleModel);
        orderModel.setReceive_salary(orderRequest.getReceive_salary());
        orderModel.setSalary_amount(orderRequest.getSalary_amount());
        if (!GeneralUtils.isNullOrEmpty(orderRequest.getDate_took_office())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date_took_office = null;
            try {
                date_took_office = dateFormat.parse(orderRequest.getDate_took_office());
                orderModel.setDate_took_office(date_took_office);
            } catch (ParseException e) {
                throw new MensageriaConsumerArgumentException(GeneralBusinessMessages.DATE.getMessage("date_took_office"));
            }
        }

        orderRepository.save(orderModel);
    }

    private void validateInputs(final OrderRequest orderRequest) {
        if (!GeneralUtils.isNumeric(Math.toIntExact(orderRequest.getClient_id()))) {
            throw new MensageriaConsumerArgumentException(GeneralBusinessMessages.NUMERIC.getMessage("client_id"));
        }
        else if (!GeneralUtils.isNumeric(Math.toIntExact(orderRequest.getRole_id()))) {
            throw new MensageriaConsumerArgumentException(GeneralBusinessMessages.NUMERIC.getMessage("role_id"));
        }
        else if (!GeneralUtils.isBoolean(orderRequest.getReceive_salary())) {
            throw new MensageriaConsumerArgumentException(GeneralBusinessMessages.BOOLEAN.getMessage("receive_salary"));
        }
        else if (orderRequest.getReceive_salary() && orderRequest.getSalary_amount() == null) {
            throw new MensageriaConsumerArgumentException("if field receive_salary is true, field salary_amount must also be informed");
        }
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
}
