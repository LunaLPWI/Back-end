package com.luna.luna_project.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class TokenController {

    @Value("${mercadopago.client.id}")
    private String clientId;

    @Value("${mercadopago.client.secret}")
    private String clientSecret;

    private final String TOKEN_URL = "https://api.mercadopago.com/oauth";

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> createOrUpdateToken(
            @RequestParam String grantType,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String refreshToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=" + grantType;

        if ("authorization_code".equals(grantType) && code != null) {
            body += "&code=" + code;
        } else if ("refresh_token".equals(grantType) && refreshToken != null) {
            body += "&refresh_token=" + refreshToken;
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
