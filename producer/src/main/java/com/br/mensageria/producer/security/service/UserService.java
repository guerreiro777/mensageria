package com.br.mensageria.producer.security.service;

import com.br.mensageria.producer.exception.MensageriaProducerArgumentException;
import com.br.mensageria.producer.exception.MensageriaProducerNotFoundException;
import com.br.mensageria.producer.security.domain.model.ERole;
import com.br.mensageria.producer.security.domain.model.SystemRolesModel;
import com.br.mensageria.producer.security.domain.model.UserModel;
import com.br.mensageria.producer.security.domain.repository.SystemRoleRepository;
import com.br.mensageria.producer.security.domain.repository.UserRepository;
import com.br.mensageria.producer.security.domain.request.UserRequest;
import com.br.mensageria.producer.security.domain.response.UserResponse;
import com.br.mensageria.producer.security.domain.response.mapper.UserResponseMapper;
import com.br.mensageria.producer.service.business.messages.GeneralBusinessMessages;
import com.br.mensageria.producer.util.GeneralUtils;
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

    @Transactional
    public void save(final UserRequest userRequest, final Long user_id) {
        validateInputs(userRequest);

        UserModel userModel = new UserModel();

        if (user_id != null) {
            Optional<UserModel> actualUser = userRepository.findById(user_id);
            if (actualUser.isEmpty()) {
                throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("User"));
            }
            if (!actualUser.get().getUsername().equals(userRequest.getUsername())) {
                checkUsernameExistsInDatabase(userRequest.getUsername());
            }
            if (!actualUser.get().getEmail().equals(userRequest.getEmail())) {
                checkEmailExistsInDatabase(userRequest.getEmail());
            }
            userModel.setId(user_id);
        } else {
            checkUsernameExistsInDatabase(userRequest.getUsername());
            checkEmailExistsInDatabase(userRequest.getEmail());
        }

        Set<String> strRoles = userRequest.getRole();
        Set<SystemRolesModel> roles = new HashSet<>();

        strRoles.forEach(role -> {
            Optional<ERole> eRole = ERole.of(role);
            if (eRole.isEmpty()) {
                throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NOT_FOUND.getMessage("role: " + eRole));
            }
            Optional<SystemRolesModel> rolesModel = systemRoleRepository.findByName(eRole.get().name());
            if (rolesModel.isEmpty()) {
                throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NOT_FOUND.getMessage("role: " + eRole));
            }
            roles.add(rolesModel.get());
        });

        userModel.setUsername(userRequest.getUsername());
        userModel.setPassword(encoder.encode(userRequest.getPassword()));
        userModel.setEmail(userRequest.getEmail());
        userModel.setRoles(roles);

        userRepository.save(userModel);
    }

    private void validateInputs(final UserRequest userRequest) {
        if (GeneralUtils.isNullOrEmpty(userRequest.getEmail())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("email"));
        }
        if (!GeneralUtils.isEmail(userRequest.getEmail())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.GENERAL_INVALID_MESSAGE.getMessage("email"));
        }
        if (GeneralUtils.isNullOrEmpty(userRequest.getUsername())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("username"));
        }
        if (GeneralUtils.isNullOrEmpty(userRequest.getPassword())) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("password"));
        }
        if (userRequest.getRole() == null || userRequest.getRole().isEmpty()) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.NULL_OR_EMPTY.getMessage("roles"));
        }
    }

    public UserModel findById(final Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty()) {
            throw new MensageriaProducerNotFoundException(GeneralBusinessMessages.NOT_FOUND.getMessage("User"));
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

    private void checkUsernameExistsInDatabase(final String username) {
        if (userRepository.existsByUsername(username)) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.EXISTS_IN_DATABASE.getMessage("username"));
        }
    }

    private void checkEmailExistsInDatabase(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new MensageriaProducerArgumentException(GeneralBusinessMessages.EXISTS_IN_DATABASE.getMessage("email"));
        }
    }

}
