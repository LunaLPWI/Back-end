package com.luna.luna_project.services;

import com.luna.luna_project.dtos.PaymentRequestDTO;
import com.luna.luna_project.models.AssinaturaRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {



    public PaymentRequestDTO paymentResponse(PaymentRequestDTO paymentRequest){

        AssinaturaRequest.builder()
                .card_token_id(paymentRequest.getToken())
                .build();

        System.out.println("Token: " + paymentRequest.getToken());
        System.out.println("Issuer ID: " + paymentRequest.getIssuer_id());
        System.out.println("Payment Method ID: " + paymentRequest.getPayment_method_id());
        System.out.println("Transaction Amount: " + paymentRequest.getTransaction_amount());
        System.out.println("Installments: " + paymentRequest.getInstallments());
        System.out.println("Description: " + paymentRequest.getDescription());
        System.out.println("Payer Email: " + paymentRequest.getPayer().getEmail());
        System.out.println("Payer Identification Type: " + paymentRequest.getPayer().getIdentification().getType());
        System.out.println("Payer Identification Number: " + paymentRequest.getPayer().getIdentification().getNumber());

        return paymentRequest;
    }
}