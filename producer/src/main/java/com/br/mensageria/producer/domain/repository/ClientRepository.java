package com.br.mensageria.producer.domain.repository;

import com.br.mensageria.producer.domain.model.ClientModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    List<ClientModel> findAll();

    Optional<ClientModel> findById(Long id);

    Optional<ClientModel> findByCpf(String cpf);

    @Query("from ClientModel where to_char(birth_date, 'mm') = :month and to_char(birth_date, 'dd') = :day")
    List<ClientModel> findAllWithBirthdayDate(String day, String month);

    List<Optional<ClientModel>> findAll(Specification<ClientModel> clientModelSpecification);
}
