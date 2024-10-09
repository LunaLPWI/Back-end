package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.PaymentRequestDTO;
import com.luna.luna_project.models.AssinaturaRequest;
import com.luna.luna_project.services.AssinaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preapproval")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class PaymentController {


    @PostMapping
    public ResponseEntity<AssinaturaRequest> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        AssinaturaRequest assinaturaRequest = AssinaturaService.criarAssinatura(paymentRequest);
        AssinaturaController.criarAssinatura(assinaturaRequest);

        return ResponseEntity.ok(assinaturaRequest);
    }

}

