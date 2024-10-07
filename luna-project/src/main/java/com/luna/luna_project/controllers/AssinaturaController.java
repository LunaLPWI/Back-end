package com.luna.luna_project.controllers;

import com.luna.luna_project.models.AssinaturaRequest;
import com.luna.luna_project.services.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
    @RequestMapping("/preapproval")
    @CrossOrigin(origins = "http://127.0.0.1:5174")
    public class AssinaturaController {

        @Autowired
        private AssinaturaService assinaturaService;

        @PostMapping
        public ResponseEntity<?> criarAssinatura(@RequestBody AssinaturaRequest request) {
            System.out.println("to aq");
            try {
                Object resultado = assinaturaService.criarAssinatura(request);
                return ResponseEntity.ok(Map.of("message", "Assinatura criada com sucesso", "data", resultado));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error Controller", e.getMessage()));
            }
        }


    }


