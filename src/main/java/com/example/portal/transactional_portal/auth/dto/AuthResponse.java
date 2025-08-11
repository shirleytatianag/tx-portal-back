package com.example.portal.transactional_portal.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

    @JsonProperty
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public static AuthResponse create(String token) {
        return new AuthResponse(token);
    }
}
