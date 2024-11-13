package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.addresses.AddressDTO;
import com.luna.luna_project.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/via-cep")
public class ViaCepController {
    @Autowired
    private ViaCepService viaCepService;

    @GetMapping("/{cep}")
    public ResponseEntity<AddressDTO> searchCep(@PathVariable String cep) {
        AddressDTO address = viaCepService.searchAddressByCep(cep);
        if (address.getCep() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }
}
