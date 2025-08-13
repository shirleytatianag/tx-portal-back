package com.example.portal.transactional_portal.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthRequest {

    @Size(max = 30)
    @NotBlank
    @JsonProperty(value = "username")
    private String username;


    @Size(max = 30)
    @NotBlank
    @JsonProperty(value = "user_password")
    private String password;
}
