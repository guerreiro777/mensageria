package com.br.mensageria.consumer.domain.repository;

import com.br.mensageria.consumer.domain.model.RoleModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> findAll();

    Optional<RoleModel> findById(Long id);

    List<RoleModel> findAll(Specification<RoleModel> roleModelSpecification);
}
