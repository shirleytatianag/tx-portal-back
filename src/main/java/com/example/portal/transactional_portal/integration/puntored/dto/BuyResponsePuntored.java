package com.example.portal.transactional_portal.integration.puntored.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BuyResponsePuntored {

    @JsonProperty(value = "transactionalID")
    private String transactionalID;

    @JsonProperty(value = "cellPhone")
    private String cellPhone;

    @JsonProperty(value = "value")
    private String value;

    @JsonProperty(value = "message")
    private String message;

    public BuyResponsePuntored(String transactionalID, String cellPhone, String value, String message) {
        this.transactionalID = transactionalID;
        this.cellPhone = cellPhone;
        this.value = value;
        this.message = message;
    }

    public static BuyResponsePuntored create(String transactionalID, String cellPhone, String value, String message) {
        return new BuyResponsePuntored(transactionalID, cellPhone, value, message);
    }
}
