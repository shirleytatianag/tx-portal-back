package com.example.portal.transactional_portal.recharge.repository;

import com.example.portal.transactional_portal.recharge.dto.RechargeResponse;
import com.example.portal.transactional_portal.recharge.entity.Recharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RechargeRepository extends JpaRepository<Recharge, UUID> {

    @Query("SELECT new com.example.portal.transactional_portal.recharge.dto.RechargeResponse(r.rechargeId, r.phoneNumber, r.operatorName, r.amount, r.status, r.puntoredTransactionId, r.ticket, r.createdAt) FROM Recharge r ORDER BY r.createdAt DESC")
    Page<RechargeResponse> getAllRecharges(Pageable pageable);
}
