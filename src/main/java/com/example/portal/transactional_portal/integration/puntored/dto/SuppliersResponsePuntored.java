package com.example.portal.transactional_portal.integration.puntored.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SuppliersResponsePuntored {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    public SuppliersResponsePuntored(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SuppliersResponsePuntored create(String id, String name) {
        return new SuppliersResponsePuntored(id, name);
    }
}
