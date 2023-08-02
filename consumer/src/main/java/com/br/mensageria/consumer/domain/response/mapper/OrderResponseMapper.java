package com.br.mensageria.consumer.domain.response.mapper;

import com.br.mensageria.consumer.domain.model.OrderModel;
import com.br.mensageria.consumer.domain.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderResponseMapper {

    OrderResponseMapper INSTANCE = Mappers.getMapper(OrderResponseMapper.class);

    @Mapping(target = "date_took_office", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "client.birth_date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "client.departure_date", dateFormat = "yyyy-MM-dd")
    OrderResponse modelToResponse(OrderModel model);
}
