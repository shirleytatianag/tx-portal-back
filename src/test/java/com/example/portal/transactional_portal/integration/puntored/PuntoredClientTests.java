package com.example.portal.transactional_portal.integration.puntored;

import com.example.portal.transactional_portal.auth.exception.AuthFailedException;
import com.example.portal.transactional_portal.integration.puntored.dto.AuthResponsePuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.BuyResponsePuntored;
import com.example.portal.transactional_portal.recharge.dto.RechargeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class PuntoredClientTest {

    @InjectMocks
    @Spy
    private PuntoredClient puntoredClient;

    private RechargeRequest rechargeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        puntoredClient.baseUrl = "http://fake-api.com";
        puntoredClient.apiKey = "fake-api-key";
        puntoredClient.user = "fakeUser";
        puntoredClient.password = "fakePass";

        rechargeRequest = new RechargeRequest("3001234567", "OP01", "Operador 1", BigDecimal.valueOf(10000));
    }

    @Test
    void authPuntored_success() {
        String fakeJson = "{\"token\":\"token123\"}";
        doReturn(fakeJson).when(puntoredClient)
                .puntoRedProxy(anyString(), eq("/auth"), eq(true), eq(false), eq(""));

        AuthResponsePuntored response = puntoredClient.authPuntored();

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("token123");
        verify(puntoredClient).puntoRedProxy(anyString(), eq("/auth"), eq(true), eq(false), eq(""));
    }

    @Test
    void authPuntored_nullResponse_throwsException() {
        doReturn(null).when(puntoredClient)
                .puntoRedProxy(anyString(), anyString(), anyBoolean(), anyBoolean(), anyString());

        assertThatThrownBy(() -> puntoredClient.authPuntored())
                .isInstanceOf(AuthFailedException.class)
                .hasMessageContaining("Tenemos problemas, reintenta más tarde...");
    }

    @Test
    void buyRecharge_success() {
        String fakeJson = "{\"transactionalID\":\"TX123\",\"message\":\"ok\"}";
        doReturn(fakeJson).when(puntoredClient)
                .puntoRedProxy(anyString(), eq("/buy"), eq(false), eq(true), eq("token123"));

        BuyResponsePuntored response = puntoredClient.buyRecharge("token123", rechargeRequest);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionalID()).isEqualTo("TX123");
        assertThat(response.getMessage()).isEqualTo("ok");
    }

    @Test
    void buyRecharge_nullResponse_throwsException() {
        doReturn(null).when(puntoredClient)
                .puntoRedProxy(anyString(), anyString(), anyBoolean(), anyBoolean(), anyString());

        assertThatThrownBy(() -> puntoredClient.buyRecharge("token123", rechargeRequest))
                .isInstanceOf(AuthFailedException.class)
                .hasMessageContaining("Tenemos problemas, reintenta más tarde...");
    }

}