package com.br.mensageria.consumer.security.service;

import com.br.mensageria.consumer.exception.MensageriaConsumerArgumentException;
import com.br.mensageria.consumer.exception.MensageriaConsumerNotFoundException;
import com.br.mensageria.consumer.security.domain.model.ERole;
import com.br.mensageria.consumer.security.domain.model.SystemRolesModel;
import com.br.mensageria.consumer.security.domain.model.UserModel;
import com.br.mensageria.consumer.security.domain.repository.SystemRoleRepository;
import com.br.mensageria.consumer.security.domain.repository.UserRepository;
import com.br.mensageria.consumer.security.domain.request.UserRequest;
import com.br.mensageria.consumer.security.domain.response.UserResponse;
import com.br.mensageria.consumer.security.domain.response.mapper.UserResponseMapper;
import com.br.mensageria.consumer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.consumer.util.GeneralUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SystemRoleRepository systemRoleRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, SystemRoleRepository systemRoleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.systemRoleRepository = systemRoleRepository;
        this.encoder = encoder;
    }

    public UserModel findById(final Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty()) {
            throw new MensageriaConsumerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("User"));
        }
        return userModel.get();
    }

    public UserResponse findById_Response(final Long id) {
        return UserResponseMapper.INSTANCE.modelToResponse(this.findById(id));
    }

    public List<UserResponse> findAll() {
        List<UserModel> users = userRepository.findAll();
        List<UserResponse> userResponseList = users.stream().map(userModel -> UserResponseMapper.INSTANCE.modelToResponse(userModel))
                .collect(Collectors.toList());
        return userResponseList;
    }
}
