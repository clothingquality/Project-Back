package com.co.quality.clothing.controllers;

import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mercadopago.resources.preference.Preference;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("qualityclothing/api/mercadopago")
public class MercadoPagoController {
    @Value("${spring.mercadopago.access.token}")
    private String accessToken;

    @PostMapping("/crear-preferencia")
    public ResponseEntity<?> crearPreferencia(@RequestBody BigDecimal total) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Compra en Quality Clothing")
                    .quantity(1)
                    .unitPrice(total)
                    .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://qualityclothingcol.com/pago-exitoso")
                    .failure("https://qualityclothingcol.com/pago-fallido")
                    .pending("https://qualityclothingcol.com/pago-pendiente")
                    .build();

            PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                    .installments(12)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .paymentMethods(paymentMethods)
                    .build();

            Preference preference = new PreferenceClient().create(preferenceRequest);

            return ResponseEntity.ok(preference.getInitPoint());

        } catch (MPApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de API de Mercado Pago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear preferencia: " + e.getMessage());
        }
    }
}
