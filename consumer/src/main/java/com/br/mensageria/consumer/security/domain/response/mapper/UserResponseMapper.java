package com.br.mensageria.consumer.security.domain.response.mapper;

import com.br.mensageria.consumer.security.domain.model.UserModel;
import com.br.mensageria.consumer.security.domain.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    UserResponse modelToResponse(UserModel model);
}
