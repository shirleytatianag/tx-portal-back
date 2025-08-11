package com.example.portal.transactional_portal.recharge.dto;

import com.example.portal.transactional_portal.recharge.entity.Recharge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class RechargeResponse {

    @JsonProperty(value = "recharge_id")
    private UUID rechargeId;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "operator_name")
    private String operatorName;

    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "puntored_transaction_id")
    private String puntoredTransactionId;

    @JsonProperty(value = "ticket")
    private String ticket;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    public RechargeResponse(UUID rechargeId, String phoneNumber, String operatorName, BigDecimal amount, String status, String puntoredTransactionId, String ticket, LocalDateTime createdAt) {
        this.rechargeId = rechargeId;
        this.phoneNumber = phoneNumber;
        this.operatorName = operatorName;
        this.amount = amount;
        this.status = status;
        this.puntoredTransactionId = puntoredTransactionId;
        this.ticket = ticket;
        this.createdAt = createdAt;
    }

    public static RechargeResponse create(Recharge recharge) {
        return new RechargeResponse(
                recharge.getRechargeId(),
                recharge.getPhoneNumber(),
                recharge.getOperatorName(),
                recharge.getAmount(),
                recharge.getStatus(),
                recharge.getPuntoredTransactionId(),
                recharge.getTicket(),
                recharge.getCreatedAt()
        );
    }
}
