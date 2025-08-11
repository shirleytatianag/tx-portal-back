package com.example.portal.transactional_portal.common.enums;

import lombok.Getter;

@Getter
public enum Messages {
    GENERIC_SYSTEM_ERROR("Tenemos problemas, reintenta más tarde..."),
    CREDENTIAL_INVALID("Credenciales incorrectas"),
    RECHARGE_NOT_FOUND("Recarga no encontrada"),
    USER_NOT_FOUND("Usuario no encontrado"),;


    private final String message;

    Messages(String message) {
        this.message = message;
    }

}
