package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.PaymentRequestDTO;
import com.luna.luna_project.models.AssinaturaRequest;
import com.luna.luna_project.services.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("https://api.mercadopago.com/")
    public class AssinaturaController {

//        @Autowired
//        private AssinaturaService assinaturaService;
//
private static final String accessToken = "TEST-5019617589040225-100518-6e036d7c0b4616976bddffffe9ba2e87-1761296678";
        @PostMapping("/preapproval")
        public static ResponseEntity<HttpEntity<AssinaturaRequest>> criarAssinatura(AssinaturaRequest request) {


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<AssinaturaRequest> entity = new HttpEntity<>(request, headers);

            try {
                System.out.println(entity);
                return ResponseEntity.ok(entity);
            } catch (Exception e) {
                System.out.println(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((AssinaturaRequest) Map.of("error Controller", e.getMessage())));
                return ResponseEntity.status(409).body(entity);

            }
//        }
        }


    }


