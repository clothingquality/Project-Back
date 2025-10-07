package com.co.quality.clothing.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailUtil {

    public boolean sendEmail(String body, String affair, String emailTo) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://qualityclothingcol.com/send_email.php";

        Map<String, String> bodyEmail = new HashMap<>();
        bodyEmail.put("to", emailTo);
        bodyEmail.put("subject", affair);
        bodyEmail.put("body", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(bodyEmail, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.getBody());

                boolean success = json.has("success") && json.get("success").asBoolean();
                String message = json.has("message") ? json.get("message").asText() : "No message";

                if (success) {
                    return true;
                } else {
                    throw new RuntimeException("Email service failed: " + message);
                }
            } else {
                throw new RuntimeException("Email service failed with HTTP status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling email service: " + e.getMessage(), e);
        }
    }
}
