package com.example.portal.transactional_portal.recharge.service.impl;

import com.example.portal.transactional_portal.auth.service.AuthService;
import com.example.portal.transactional_portal.common.enums.Messages;
import com.example.portal.transactional_portal.common.exception.NotFoundException;
import com.example.portal.transactional_portal.integration.puntored.PuntoredClient;
import com.example.portal.transactional_portal.integration.puntored.dto.AuthResponsePuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.BuyResponsePuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.SuppliersResponsePuntored;
import com.example.portal.transactional_portal.recharge.dto.RechargeRequest;
import com.example.portal.transactional_portal.recharge.dto.RechargeResponse;
import com.example.portal.transactional_portal.recharge.entity.Recharge;
import com.example.portal.transactional_portal.recharge.repository.RechargeRepository;
import com.example.portal.transactional_portal.recharge.service.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.example.portal.transactional_portal.common.enums.Messages.GENERIC_SYSTEM_ERROR;

@Service
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    private final RechargeRepository rechargeRepository;
    private final AuthService authService;
    private final PuntoredClient puntoredClient;

    public RechargeServiceImpl(RechargeRepository rechargeRepository, AuthService authService, PuntoredClient puntoredClient) {
        this.rechargeRepository = rechargeRepository;
        this.authService = authService;
        this.puntoredClient = puntoredClient;
    }

    @Override
    public RechargeResponse createRecharge(RechargeRequest rechargeRequest) {
        this.validateRecharge(rechargeRequest);

        Recharge recharge = rechargeRepository.save(
                Recharge.create(
                        rechargeRequest.getAmount(),
                        rechargeRequest.getPhoneNumber(),
                        rechargeRequest.getOperatorId(),
                        rechargeRequest.getOperatorName(),
                        "COP",
                        "PENDING",
                        null,
                        null,
                        authService.getUserAuthenticated()
                )
        );

        AuthResponsePuntored authResponsePuntored = puntoredClient.authPuntored();
        BuyResponsePuntored buyResponse = puntoredClient.buyRecharge(authResponsePuntored.getToken(), rechargeRequest);
        recharge.updateResponsePuntored(buyResponse.getTransactionalID(), buyResponse.getMessage(), "SUCCESS");
        rechargeRepository.save(recharge);

        return RechargeResponse.create(recharge);
    }

    @Override
    public RechargeResponse getRechargeById(UUID rechargeId) {
        Recharge recharge = rechargeRepository.findById(rechargeId).orElseThrow(() ->
                new NotFoundException(Messages.RECHARGE_NOT_FOUND.getMessage()));
        return RechargeResponse.create(recharge);
    }

    @Override
    public List<SuppliersResponsePuntored> getSuppliers() {
        AuthResponsePuntored authResponsePuntored = puntoredClient.authPuntored();

        List<SuppliersResponsePuntored> suppliers = puntoredClient.getSuppliers(authResponsePuntored.getToken());
        if (suppliers == null) {
            throw new NotFoundException(GENERIC_SYSTEM_ERROR.getMessage());
        }

        return suppliers;
    }

    @Override
    public Page<RechargeResponse> getRecharges(String pageNumber) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), 12);
        return rechargeRepository.getAllRecharges(pageable);
    }

    private void validateRecharge(RechargeRequest rechargeRequest) {
        if (!rechargeRequest.getPhoneNumber().matches("^3\\d{9}$")) {
            throw new IllegalArgumentException("Número de teléfono invalido");
        }

        if (rechargeRequest.getAmount().compareTo(BigDecimal.valueOf(1000)) < 0 ||
                rechargeRequest.getAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
            throw new IllegalArgumentException("Monto fuera de rango");
        }
    }
}
