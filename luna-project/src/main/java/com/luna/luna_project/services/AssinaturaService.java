package com.luna.luna_project.services;


import ch.qos.logback.classic.Logger;
import com.luna.luna_project.dtos.PaymentRequestDTO;
import com.luna.luna_project.models.AssinaturaRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AssinaturaService {
    private final String accessToken = "TEST-5019617589040225-100518-6e036d7c0b4616976bddffffe9ba2e87-1761296678";
    private final String url = "https://api.mercadopago.com/preapproval";

    public static AssinaturaRequest criarAssinatura(PaymentRequestDTO request) {

        AssinaturaRequest assinaturaRequest =
        AssinaturaRequest.builder()
                .card_token_id(request.getToken())
                .payer_email(request.getPayer().getEmail())
                .back_url("www.google.com.br")
                .reason("Teste de criação deassinatura")
                .status("authorized")
                .auto_recurring(AssinaturaRequest.AutoRecurring.builder()
                        .frequency(1)
                        .frequency_type("months")
                        .start_date("2024-10-05T12:00:00Z")
                        .end_date("2025-10-05T12:00:00Z")
                        .transaction_amount(10.00)
                        .currency_id("BRL")
                        .build())
                .build();
        return assinaturaRequest;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + accessToken);
//
//        HttpEntity<AssinaturaRequest> entity = new HttpEntity<>(assinaturaRequest, headers);
//
//        Logger logger = null;
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//            logger.error("Erro ao criar assinatura: {}", e.getResponseBodyAsString());
//            return "Erro ao criar assinatura: " + e.getResponseBodyAsString();
//        } catch (Exception e) {
//            logger.error("Erro inesperado: {}", e.getMessage());
//            return "Erro inesperado: " + e.getMessage();
//        }
    }
}


//axios.post('/mercado-pago', {
//    id: "123"
//})
//
//
//if (nãoteminfor) {
//        axios.post('/mercado-pago', {
//    id: "123"
//})
//        }
//
//
//client > post
//api > get depois de gravar os dados
//api > retorna a response entity no POST que o client solicitou, o get foi feito debaixo dos panos
