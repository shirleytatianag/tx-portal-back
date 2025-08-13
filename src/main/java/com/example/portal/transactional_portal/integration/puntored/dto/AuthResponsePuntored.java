package com.example.portal.transactional_portal.integration.puntored.dto;

import lombok.Getter;

@Getter
public class AuthResponsePuntored {

    private String token;

    public AuthResponsePuntored(String token) {
        this.token = token;
    }

}
