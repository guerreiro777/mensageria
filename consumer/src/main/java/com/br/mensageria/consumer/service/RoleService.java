package com.br.mensageria.consumer.service;

import com.br.mensageria.consumer.domain.model.RoleModel;
import com.br.mensageria.consumer.domain.repository.RoleRepository;
import com.br.mensageria.consumer.domain.request.RoleRequest;
import com.br.mensageria.consumer.domain.response.RoleResponse;
import com.br.mensageria.consumer.domain.response.mapper.RoleResponseMapper;
import com.br.mensageria.consumer.exception.MensageriaConsumerArgumentException;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.consumer.util.GeneralUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleModel findById(final Long id) {
        Optional<RoleModel> roleModel = roleRepository.findById(id);
        if (roleModel.isEmpty()) {
            throw new MensageriaConsumerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Role"));
        }
        return roleModel.get();
    }

    public List<RoleResponse> findAll() {
        List<RoleModel> roles = roleRepository.findAll();
        List<RoleResponse> roleResponseList = roles.stream().map(roleModel -> RoleResponseMapper.INSTANCE.modelToResponse(roleModel))
                .collect(Collectors.toList());
        return roleResponseList;
    }

    public List<RoleResponse> filter(RoleRequest roleRequest) {
        List<RoleModel> roles = roleRepository.findAll((Specification<RoleModel>)
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!GeneralUtils.isNullOrEmpty(roleRequest.getDescription())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("description")), "%" +
                                        roleRequest.getDescription().toUpperCase() + "%")));
                    }
                    if (!GeneralUtils.isNullOrEmpty(roleRequest.getRole_name())) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("role_name")), "%" +
                                        roleRequest.getRole_name().toUpperCase() + "%")));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                });

        List<RoleResponse> roleResponseList = roles.stream()
                .map(roleModel -> RoleResponseMapper.INSTANCE.modelToResponse(roleModel))
                .sorted(Comparator.comparing(RoleResponse::getRole_name))
                .collect(Collectors.toList());

        return roleResponseList;
    }
}
