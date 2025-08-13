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
import com.example.portal.transactional_portal.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RechargeServiceImplTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @Mock
    private AuthService authService;

    @Mock
    private PuntoredClient puntoredClient;

    @InjectMocks
    private RechargeServiceImpl rechargeService;

    private RechargeRequest rechargeRequest;
    private Recharge rechargeEntity;
    private User userEntity;
    private AuthResponsePuntored authResponsePuntored;
    private BuyResponsePuntored buyResponsePuntored;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        rechargeRequest = new RechargeRequest("3001234567","8753", "Claro", BigDecimal.valueOf(10000));

        rechargeEntity = Recharge.create(
                rechargeRequest.getAmount(),
                rechargeRequest.getPhoneNumber(),
                rechargeRequest.getOperatorId(),
                rechargeRequest.getOperatorName(),
                "COP",
                "PENDING",
                null,
                null,
                userEntity
        );

        authResponsePuntored = new AuthResponsePuntored("token123");


        buyResponsePuntored = new BuyResponsePuntored("cc6982b2-398d-48a2-ba3c-93380444a8f0", "3001234567", "10000", "Recarga realizada con éxito");

    }

    @Test
    void createRecharge_success() {
        when(authService.getUserAuthenticated()).thenReturn(userEntity);
        when(rechargeRepository.save(any(Recharge.class))).thenReturn(rechargeEntity);
        when(puntoredClient.authPuntored()).thenReturn(authResponsePuntored);
        when(puntoredClient.buyRecharge(anyString(), any())).thenReturn(buyResponsePuntored);

        RechargeResponse response = rechargeService.createRecharge(rechargeRequest);

        assertThat(response).isNotNull();
        verify(rechargeRepository, times(2)).save(any(Recharge.class));
        verify(puntoredClient).authPuntored();
        verify(puntoredClient).buyRecharge("token123", rechargeRequest);
    }

    @Test
    void createRecharge_invalidPhoneNumber_throwsException() {
        rechargeRequest = new RechargeRequest("2001234567","8753", "Claro", BigDecimal.valueOf(10000));

        assertThatThrownBy(() -> rechargeService.createRecharge(rechargeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Número de teléfono invalido");
    }

    @Test
    void createRecharge_invalidAmountLow_throwsException() {
        rechargeRequest = new RechargeRequest("3001234567","8753", "Claro", BigDecimal.valueOf(500));

        assertThatThrownBy(() -> rechargeService.createRecharge(rechargeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Monto fuera de rango");
    }

    @Test
    void createRecharge_invalidAmountHigh_throwsException() {
        rechargeRequest = new RechargeRequest("3001234567","8753", "Claro", BigDecimal.valueOf(200000));

        assertThatThrownBy(() -> rechargeService.createRecharge(rechargeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Monto fuera de rango");
    }

    @Test
    void getRechargeById_found() {
        UUID id = UUID.randomUUID();
        when(rechargeRepository.findById(id)).thenReturn(Optional.of(rechargeEntity));

        RechargeResponse response = rechargeService.getRechargeById(id);

        assertThat(response).isNotNull();
        verify(rechargeRepository).findById(id);
    }

    @Test
    void getRechargeById_notFound() {
        UUID id = UUID.randomUUID();
        when(rechargeRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rechargeService.getRechargeById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(Messages.RECHARGE_NOT_FOUND.getMessage());
    }

    @Test
    void getSuppliers_success() {
        List<SuppliersResponsePuntored> suppliers = List.of(new SuppliersResponsePuntored("123", "Claro"));
        when(puntoredClient.authPuntored()).thenReturn(authResponsePuntored);
        when(puntoredClient.getSuppliers("token123")).thenReturn(suppliers);

        List<SuppliersResponsePuntored> result = rechargeService.getSuppliers();

        assertThat(result).hasSize(1);
        verify(puntoredClient).authPuntored();
        verify(puntoredClient).getSuppliers("token123");
    }

    @Test
    void getSuppliers_nullList_throwsException() {
        when(puntoredClient.authPuntored()).thenReturn(authResponsePuntored);
        when(puntoredClient.getSuppliers("token123")).thenReturn(null);

        assertThatThrownBy(() -> rechargeService.getSuppliers())
                .isInstanceOf(NotFoundException.class)
                .hasMessage(Messages.GENERIC_SYSTEM_ERROR.getMessage());
    }

    @Test
    void getRecharges_success() {
        Page<RechargeResponse> page = new PageImpl<>(List.of(RechargeResponse.create(rechargeEntity)));
        when(rechargeRepository.getAllRecharges(any(Pageable.class))).thenReturn(page);

        Page<RechargeResponse> result = rechargeService.getRecharges("0");

        assertThat(result).hasSize(1);
        verify(rechargeRepository).getAllRecharges(any(Pageable.class));
    }
}
