package com.luna.luna_project.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiGerenciaNetService {

    @Value("${gerenciaNet.api.url}")
    private String apiUrl;

    @Value("${gerenciaNet.api.token}")
    private String accessToken;

    public String createSubscription() {
        String url = apiUrl + "/v1/subscription"; // Endpoint para assinaturas

        // Criando o JSON usando JSONObject
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("name", "Monthly Subscription");
        jsonPayload.put("description", "Access to premium content");
        jsonPayload.put("value", 1000); // Valor da assinatura em centavos
        jsonPayload.put("interval", "1");
        jsonPayload.put("frequency", "MONTH");

        // Criando o objeto customer
        JSONObject customer = new JSONObject();
        customer.put("name", "Customer Name");
        customer.put("email", "customer@example.com");
        jsonPayload.put("customer", customer);

        // Adicionando detalhes do pagamento
        JSONObject paymentDetails = new JSONObject();
        paymentDetails.put("cardToken", "YOUR_CARD_TOKEN"); // Substitua pelo token do cartão
        jsonPayload.put("paymentMethod", "CREDIT_CARD");
        jsonPayload.put("paymentDetails", paymentDetails);

        // Configurando a requisição
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(jsonPayload.toString(), headers);

        // Enviando a requisição
        String response = restTemplate.postForObject(url, request, String.class);
        return response;
    }
}
