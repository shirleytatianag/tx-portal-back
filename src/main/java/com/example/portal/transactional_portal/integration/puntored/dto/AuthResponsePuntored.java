package com.example.portal.transactional_portal.integration.puntored.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthResponsePuntored {

    @JsonProperty
    private String token;

    public AuthResponsePuntored(String token) {
        this.token = token;
    }

    public static AuthResponsePuntored create(String token) {
        return new AuthResponsePuntored(token);
    }
}
