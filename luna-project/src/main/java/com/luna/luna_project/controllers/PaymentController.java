package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.PaymentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process-payment")
@CrossOrigin(origins = "http://127.0.0.1:5174")
public class PaymentController {

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        // Aqui você pode processar o pagamento
        System.out.println("Token: " + paymentRequest.getToken());
        System.out.println("Issuer ID: " + paymentRequest.getIssuer_id());
        System.out.println("Payment Method ID: " + paymentRequest.getPayment_method_id());
        System.out.println("Transaction Amount: " + paymentRequest.getTransaction_amount());
        System.out.println("Installments: " + paymentRequest.getInstallments());
        System.out.println("Description: " + paymentRequest.getDescription());
        System.out.println("Payer Email: " + paymentRequest.getPayer().getEmail());
        System.out.println("Payer Identification Type: " + paymentRequest.getPayer().getIdentification().getType());
        System.out.println("Payer Identification Number: " + paymentRequest.getPayer().getIdentification().getNumber());

        // Retornar uma resposta apropriada
        return ResponseEntity.ok("Payment processed successfully");
    }
}

