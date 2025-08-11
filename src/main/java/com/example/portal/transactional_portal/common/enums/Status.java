package com.example.portal.transactional_portal.common.enums;

import lombok.Getter;

@Getter
public enum Status {
    APPROVED("Aprobado"),
    REFUSED("Rechezado"),
    PENDING("Pendiente");

    private final String status;


    Status(String status) {
        this.status = status;
    }
}
