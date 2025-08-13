package com.example.portal.transactional_portal.integration.puntored.dto;

import lombok.Getter;

@Getter
public class BuyResponsePuntored {

    private String transactionalID;

    private String cellPhone;

    private String value;

    private String message;

    public BuyResponsePuntored(String transactionalID, String cellPhone, String value, String message) {
        this.transactionalID = transactionalID;
        this.cellPhone = cellPhone;
        this.value = value;
        this.message = message;
    }

}
