package com.co.quality.clothing.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmailUtil {

    @Value("${resend.api.key}")
    private String apiKey;

    public boolean sendEmail(String body, String affair, String emailTo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> bodyEmail = new HashMap<>();
        bodyEmail.put("from", "Quality Clothing <no-reply@qualityclothingcol.com>");
        bodyEmail.put("to", List.of(emailTo));
        bodyEmail.put("subject", affair);
        bodyEmail.put("html", body);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(bodyEmail, headers);

        try {
            restTemplate.postForEntity("https://api.resend.com/emails", request, String.class);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo: " + e.getMessage(), e);
        }
    }
}
