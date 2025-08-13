package com.example.portal.transactional_portal.integration.puntored.dto;

import lombok.Getter;

@Getter
public class SuppliersResponsePuntored {

    private String id;

    private String name;

    public SuppliersResponsePuntored(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
