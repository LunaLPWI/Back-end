package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Busca todos os clientes
    @GetMapping
    public ResponseEntity<List<ClientDTO>> searchClients() {
        List<ClientDTO> clients = clientService.searchClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(clients);
    }

    // Busca cliente por CPF usando @RequestParam
    @GetMapping("/search-by-cpf")
    public ResponseEntity<ClientDTO> searchClientByCpf(@RequestParam String cpf) {
        ClientDTO client = clientService.searchClientByCpf(cpf);
        if (client == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(client);
    }

    // Cria um novo cliente
    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
        if (clientService.existsCpf(clientDTO.cpf())) {
            return ResponseEntity.status(409).build(); // Conflict - CPF já existente
        }
        ClientDTO client = clientService.saveClient(clientDTO, clientDTO.address());
        return ResponseEntity.ok().body(client);
    }

    // Deleta cliente por CPF usando @RequestParam
    @DeleteMapping("/delete-by-cpf")
    public ResponseEntity<String> deleteClientByCpf(@RequestParam String cpf) {
        try {
            clientService.deleteClient(cpf);
            return ResponseEntity.ok().body("Cliente desativado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Busca cliente por ID usando @PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> searchClientById(@PathVariable Long id) {
        ClientDTO client = clientService.searchClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/sorted")
    public List<ClientDTO> getSortedClients() {
        return clientService.sortClientsByName();
    }
}
