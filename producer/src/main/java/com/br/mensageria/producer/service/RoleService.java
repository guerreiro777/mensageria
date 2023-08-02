package com.br.mensageria.producer.service;

import com.br.mensageria.producer.domain.model.RoleModel;
import com.br.mensageria.producer.domain.repository.RoleRepository;
import com.br.mensageria.producer.domain.request.RoleRequest;
import com.br.mensageria.producer.domain.response.RoleResponse;
import com.br.mensageria.producer.domain.response.mapper.RoleResponseMapper;
import com.br.mensageria.producer.exception.MensageriaProducerArgumentException;
import com.br.mensageria.producer.exception.MensageriaProducerNotFoundException;
import com.br.mensageria.producer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.producer.util.GeneralUtils;
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

    @Transactional
    public void save(final RoleRequest roleRequest, final Long role_id) {
        validateInputs(roleRequest);

        RoleModel roleModel = new RoleModel();

        if (role_id != null) {
            if (roleRepository.findById(role_id).isEmpty()) {
                throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Role"));
            }
            roleModel.setId(role_id);
        }

        roleModel.setRole_name(roleRequest.getRole_name());
        roleModel.setDescription(roleRequest.getDescription());
        roleModel.setIs_ecclesiastical_function(roleRequest.getIs_ecclesiastical_function());
        roleModel.setHierarchical_order(roleRequest.getHierarchical_order());

        roleRepository.save(roleModel);
    }

    private void validateInputs(final RoleRequest roleRequest) {
        if (GeneralUtils.isNullOrEmpty(roleRequest.getRole_name())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("role_name"));
        }
        else if (GeneralUtils.isNullOrEmpty(roleRequest.getDescription())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("description"));
        }
        else if (!GeneralUtils.isBoolean(roleRequest.getIs_ecclesiastical_function())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.BOOLEAN.getMessage("is_ecclesiastical_function"));
        }
        else if (!GeneralUtils.isNumeric(roleRequest.getHierarchical_order())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NUMERIC.getMessage("hierarchical_order"));
        }
    }


    public RoleModel findById(final Long id) {
        Optional<RoleModel> roleModel = roleRepository.findById(id);
        if (roleModel.isEmpty()) {
            throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Role"));
        }
        return roleModel.get();
    }

    public RoleResponse findById_Response(final Long id) {
        return RoleResponseMapper.INSTANCE.modelToResponse(this.findById(id));
    }

    public List<RoleResponse> findAll() {
        List<RoleModel> roles = roleRepository.findAll();
        List<RoleResponse> roleResponseList = roles.stream().map(roleModel -> RoleResponseMapper.INSTANCE.modelToResponse(roleModel))
                .collect(Collectors.toList());
        return roleResponseList;
    }

    public void deleteById(Long id) {
        RoleModel roleModel = this.findById(id);
        if (Objects.isNull(roleModel)) {
            throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("Role"));
        }
        roleRepository.deleteById(roleModel.getId());
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
