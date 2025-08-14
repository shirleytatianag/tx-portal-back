package com.example.portal.transactional_portal.integration.puntored;

import com.example.portal.transactional_portal.auth.exception.AuthFailedException;
import com.example.portal.transactional_portal.integration.puntored.dto.AuthResponsePuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.BuyRequestPuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.BuyResponsePuntored;
import com.example.portal.transactional_portal.integration.puntored.dto.SuppliersResponsePuntored;
import com.example.portal.transactional_portal.recharge.dto.RechargeRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static com.example.portal.transactional_portal.common.enums.Messages.GENERIC_SYSTEM_ERROR;


@Service
@Slf4j
public class PuntoredClient {

    @Value("${settings.puntored.base-url}")
    public String baseUrl;

    @Value("${settings.puntored.api-key}")
    public String apiKey;

    @Value("${settings.puntored.user}")
    public String user;

    @Value("${settings.puntored.password}")
    public String password;

    String contentType = "application/json";

    private final Gson gson = new Gson();

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String puntoRedProxy(String jsonRequest, String endpoint, boolean includeXApiKey, boolean includeAuthorization, String token) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .timeout(Duration.ofSeconds(10));

            if (includeXApiKey) {
                requestBuilder.header("x-api-key", apiKey);
            }

            if (includeAuthorization) {
                requestBuilder.header("Authorization", token);
            }

            HttpRequest httpRequest = requestBuilder.build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 400) {
                throw new BadRequestException(httpResponse.body());
            }
            return httpResponse.body();

        } catch (Exception ex) {
            log.error("Se produjo un error al realizar la petición a la API de Punto Red. {}", ex.getMessage());
            return null;
        }
    }

    public AuthResponsePuntored authPuntored() {
        String jsonRequest = "{\"user\":\"" + user + "\",\"password\":\"" + password + "\"}";

        log.info("Request Auth PuntoRed: {}", jsonRequest);
        String httpResponse = puntoRedProxy(jsonRequest, "/auth", true, false, "");

        if (httpResponse == null) {
            throw new AuthFailedException(GENERIC_SYSTEM_ERROR.getMessage());
        }

        log.info("Response Auth PuntoRed: {}", httpResponse);
        AuthResponsePuntored authResponsePuntored = gson.fromJson(httpResponse, AuthResponsePuntored.class);
        return new AuthResponsePuntored(authResponsePuntored.getToken());

    }

    public BuyResponsePuntored buyRecharge(String token, RechargeRequest rechargeRequest) {
        String jsonRequest = new BuyRequestPuntored(
                rechargeRequest.getOperatorId(),
                rechargeRequest.getPhoneNumber(),
                rechargeRequest.getAmount()
        ).toString();

        log.info("Request BuyRecharge PuntoRed: {}", jsonRequest);
        String httpResponse = puntoRedProxy(jsonRequest, "/buy", false, true, token);

        if (httpResponse == null) {
            throw new AuthFailedException(GENERIC_SYSTEM_ERROR.getMessage());
        }

        log.info("Response BuyRecharge PuntoRed: {}", httpResponse);
        return gson.fromJson(httpResponse, BuyResponsePuntored.class);
    }

    public List<SuppliersResponsePuntored> getSuppliers(String token) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/getSuppliers"))
                    .header("Authorization", token)
                    .GET()
                    .timeout(Duration.ofSeconds(10));

            HttpRequest httpRequest = requestBuilder.build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Response getSuppliers PuntoRed: {}", httpResponse);

            Type listType = new TypeToken<List<SuppliersResponsePuntored>>() {
            }.getType();

            return gson.fromJson(httpResponse.body(), listType);
        } catch (Exception ex) {
            log.error("error al solicitar los Suppliers {}", ex.getMessage());
            return null;
        }
    }

}

