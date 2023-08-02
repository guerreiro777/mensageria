package com.br.mensageria.consumer.security.domain.model;

import java.util.Optional;

public enum ERole {
    ROLE_ADMIN("admin"),
    ROLE_MODERATOR("moderator"),
    ROLE_USER("user");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public static Optional<ERole> of(final String role)
    {
        for (final ERole operationType : ERole.values())
        {
            if (operationType.role.equals(role))
            {
                return Optional.of(operationType);
            }
        }
        return Optional.empty();
    }
}