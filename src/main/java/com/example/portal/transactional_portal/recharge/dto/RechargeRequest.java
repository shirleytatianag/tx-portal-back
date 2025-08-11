package com.example.portal.transactional_portal.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RechargeRequest {

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "operator_id")
    private String operatorId;

    @JsonProperty(value = "operator_name")
    private String operatorName;

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    public RechargeRequest(String phoneNumber, String operatorId, String operatorName, BigDecimal amount) {
        this.phoneNumber = phoneNumber;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.amount = amount;
    }
}
