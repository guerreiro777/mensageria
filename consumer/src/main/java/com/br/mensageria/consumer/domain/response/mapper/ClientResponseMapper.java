package com.br.mensageria.consumer.domain.response.mapper;

import com.br.mensageria.consumer.domain.model.ClientModel;
import com.br.mensageria.consumer.domain.response.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientResponseMapper {

    ClientResponseMapper INSTANCE = Mappers.getMapper(ClientResponseMapper.class);

    @Mapping(target = "birth_date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "departure_date", dateFormat = "yyyy-MM-dd")
    ClientResponse modelToResponse(ClientModel model);
}
