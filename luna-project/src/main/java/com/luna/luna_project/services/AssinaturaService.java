package com.luna.luna_project.services;


import ch.qos.logback.classic.Logger;
import com.luna.luna_project.dtos.PaymentRequestDTO;
import com.luna.luna_project.models.AssinaturaRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

@Service
public class AssinaturaService {
    private static final String accessToken = "TEST-5019617589040225-100518-6e036d7c0b4616976bddffffe9ba2e87-1761296678";
    private static final String url = "https://api.mercadopago.com/preapproval";

    public static AssinaturaRequest criarAssinatura(PaymentRequestDTO request) {

        AssinaturaRequest assinaturaRequest =
        AssinaturaRequest.builder()
                .card_token_id(request.getToken())
                .payer_email(request.getPayer().getEmail())
                .back_url("www.google.com.br")
                .reason("Teste de criação deassinatura")
                .status("Unauthorized")
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



//        try {
//            RestOperations restTemplate = null;
//            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//            return response.getBody();
//        } catch (HttpClientErrorException e) {
//
//            return "Erro ao criar assinatura: " + e.getResponseBodyAsString();
//        } catch (Exception e) {
//
//            return "Erro inesperado: " + e.getMessage();
//        }
    }
}









//package com.example.mercadopago;
//
//import com.mercadopago.MercadoPagoConfig;
//import com.mercadopago.client.preference.PreferenceClient;
//import com.mercadopago.client.preference.PreferenceItemRequest;
//import com.mercadopago.client.preference.PreferenceRequest;
//import com.mercadopago.exceptions.MPException;
//import com.mercadopago.resources.preference.Preference;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//        import java.math.BigDecimal;
//import java.util.Collections;
//
//@SpringBootApplication
//@RestController
//@RequestMapping("/api/subscriptions")
//public class SubscriptionApplication {
//
//    @Value("${mercadopago.access.token}")
//    private String mercadoPagoAccessToken;
//
//    public static void main(String[] args) {
//        SpringApplication.run(SubscriptionApplication.class, args);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> createSubscription(@RequestBody SubscriptionRequest request) {
//        try {
//            // Configure o SDK com o seu token de acesso
//            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
//
//            // Crie um cliente de preferência
//            PreferenceClient client = new PreferenceClient();
//
//            // Crie um item para a assinatura
//            PreferenceItemRequest item = PreferenceItemRequest.builder()
//                    .title(request.getReason())
//                    .quantity(1)
//                    .unitPrice(new BigDecimal(request.getAutoRecurring().getTransactionAmount()))
//                    .currencyId(request.getAutoRecurring().getCurrencyId())
//                    .build();
//
//            // Crie uma requisição de preferência
//            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
//                    .items(Collections.singletonList(item))
//                    .payer(request.toPayer())
//                    .backUrls(request.toBackUrls())
//                    .autoReturn("approved")
//                    .externalReference(request.getExternalReference())
//                    .build();
//
//            // Crie a preferência
//            Preference preference = client.create(preferenceRequest);
//
//            // Retorne o ID da preferência criada
//            return ResponseEntity.ok(preference.getId());
//
//        } catch (MPException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error creating subscription: " + e.getMessage());
//        }
//    }
//}
//
//class SubscriptionRequest {
//    private String preapprovalPlanId;
//    private String reason;
//    private String externalReference;
//    private String payerEmail;
//    private String cardTokenId;
//    private AutoRecurring autoRecurring;
//    private String backUrl;
//    private String status;
//
//    // Getters and setters omitted for brevity
//
//    public com.mercadopago.client.preference.PreferencePayerRequest toPayer() {
//        return com.mercadopago.client.preference.PreferencePayerRequest.builder()
//                .email(this.payerEmail)
//                .build();
//    }
//
//    public com.mercadopago.client.preference.PreferenceBackUrlsRequest toBackUrls() {
//        return com.mercadopago.client.preference.PreferenceBackUrlsRequest.builder()
//                .success(this.backUrl)
//                .failure(this.backUrl)
//                .pending(this.backUrl)
//                .build();
//    }
//
//    static class AutoRecurring {
//        private int frequency;
//        private String frequencyType;
//        private double transactionAmount;
//        private String currencyId;
//
//        // Getters and setters omitted for brevity
//    }
//}
//

