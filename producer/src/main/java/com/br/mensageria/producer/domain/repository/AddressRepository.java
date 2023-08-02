package com.br.mensageria.producer.domain.repository;

import com.br.mensageria.producer.domain.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
    List<AddressModel> findAll();
    Optional<AddressModel> findById(Long id);
}
