package com.luna.luna_project.controllers;

import com.luna.luna_project.models.AssinaturaRequest;
import com.luna.luna_project.services.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("https://api.mercadopago.com")
    public class AssinaturaController {

        @Autowired
        private AssinaturaService assinaturaService;

        @PostMapping("/preapproval")
        public ResponseEntity<?> criarAssinatura(@RequestBody AssinaturaRequest request) {
            try {
                return ResponseEntity.ok(assinaturaService.criarAssinatura(request));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }


    }


