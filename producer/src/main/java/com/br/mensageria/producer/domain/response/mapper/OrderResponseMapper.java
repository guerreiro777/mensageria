package com.br.mensageria.producer.domain.response.mapper;

import com.br.mensageria.producer.domain.model.OrderModel;
import com.br.mensageria.producer.domain.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderResponseMapper {

    OrderResponseMapper INSTANCE = Mappers.getMapper(OrderResponseMapper.class);

    OrderResponse modelToResponse(OrderModel model);
}
