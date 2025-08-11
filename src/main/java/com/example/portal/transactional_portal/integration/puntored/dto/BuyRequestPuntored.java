package com.example.portal.transactional_portal.integration.puntored.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BuyRequestPuntored {

    @JsonProperty(value = "supplierId")
    private String supplierId;

    @JsonProperty(value = "cellPhone")
    private String cellPhone;

    @JsonProperty(value = "value")
    private BigDecimal value;

    public BuyRequestPuntored(String supplierId, String cellPhone, BigDecimal value) {
        this.supplierId = supplierId;
        this.cellPhone = cellPhone;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "\"supplierId\":\"" + supplierId + "\"" +
                ", \"cellPhone\":\"" + cellPhone + "\"" +
                ", \"value\":\"" + value + "\"" +
                "}";
    }
}
