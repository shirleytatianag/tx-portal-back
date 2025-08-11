package com.example.portal.transactional_portal.recharge.entity;

import com.example.portal.transactional_portal.common.util.AuditEntity;
import com.example.portal.transactional_portal.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "recharge", schema = "main")
public class Recharge extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "recharge_id")
    private UUID rechargeId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "operator_id", nullable = false)
    private String operatorId;

    @Column(name = "operator_name", nullable = false)
    private String operatorName;


    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "puntored_transaction_id", length = 100)
    private String puntoredTransactionId;

    @Column(columnDefinition = "TEXT")
    private String ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Recharge(BigDecimal amount, String phoneNumber, String operatorId, String operatorName, String currency, String status, String puntoredTransactionId, String ticket, User user) {
        this.amount = amount;
        this.phoneNumber = phoneNumber;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.currency = currency;
        this.status = status;
        this.puntoredTransactionId = puntoredTransactionId;
        this.ticket = ticket;
        this.user = user;
    }

    public static Recharge create(BigDecimal amount, String phoneNumber, String operatorId, String operatorName, String currency, String status, String puntoredTransactionId, String ticket, User user) {
        return new Recharge(
                amount,
                phoneNumber,
                operatorId,
                operatorName,
                currency,
                status,
                puntoredTransactionId,
                ticket,
                user
        );
    }

    public void updateResponsePuntored(String puntoredTransactionId, String ticket, String status) {
        this.puntoredTransactionId = puntoredTransactionId;
        this.ticket = ticket;
        this.status = status;
    }

}
