package com.br.mensageria.consumer.domain.repository;

import com.br.mensageria.consumer.domain.model.OrderModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findAll();
    Optional<OrderModel> findById(Long id);

    List<Optional<OrderModel>> findAll(Specification<OrderModel> orderModelSpecification);
}
