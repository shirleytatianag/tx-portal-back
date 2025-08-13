package com.example.portal.transactional_portal.integration.puntored.dto;

import java.math.BigDecimal;

public class BuyRequestPuntored {

    private String supplierId;

    private String cellPhone;

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
