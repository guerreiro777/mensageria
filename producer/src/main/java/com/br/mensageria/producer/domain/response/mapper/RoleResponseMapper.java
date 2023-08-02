package com.br.mensageria.producer.domain.response.mapper;

import com.br.mensageria.producer.domain.model.RoleModel;
import com.br.mensageria.producer.domain.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleResponseMapper {

    RoleResponseMapper INSTANCE = Mappers.getMapper(RoleResponseMapper.class);

    RoleResponse modelToResponse(RoleModel model);
}
