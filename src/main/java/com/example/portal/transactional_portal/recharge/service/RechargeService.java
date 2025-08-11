package com.example.portal.transactional_portal.recharge.service;

import com.example.portal.transactional_portal.integration.puntored.dto.SuppliersResponsePuntored;
import com.example.portal.transactional_portal.recharge.dto.RechargeRequest;
import com.example.portal.transactional_portal.recharge.dto.RechargeResponse;

import java.util.List;
import java.util.UUID;

public interface RechargeService {

    RechargeResponse createRecharge(RechargeRequest rechargeRequest);

    RechargeResponse getRechargeById(UUID rechargeId);

    List<SuppliersResponsePuntored> getSuppliers();
}
