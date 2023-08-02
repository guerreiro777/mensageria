package com.br.mensageria.producer.security.domain.repository;

import com.br.mensageria.producer.security.domain.model.SystemRolesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRolesModel, Long> {
    Optional<SystemRolesModel> findByName(String name);
}
