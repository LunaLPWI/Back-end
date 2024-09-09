package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.AddressRequestDTO;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.repositories.AddressRepository;
import com.luna.luna_project.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
