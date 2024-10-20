package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.mapper.ClientMapperImpl;
import com.luna.luna_project.models.Client;
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
    @Autowired
    private ClientMapperImpl clientMapperImpl;


    @GetMapping
    public ResponseEntity<List<ClientDTO>> searchClients() {
        List<ClientDTO> clients = clientService.searchClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(clients);
    }


    @GetMapping("/search-by-cpf")
    public ResponseEntity<ClientDTO> searchClientByCpf(@RequestParam String cpf) {
        ClientDTO client = clientService.searchClientByCpf(cpf);
        if (client == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(client);
    }


    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody @Valid ClientDTO clientDTO) {
        if (clientService.existsCpf(clientDTO.cpf())) {
            return ResponseEntity.status(409).build();
        }
        ClientDTO client = clientService.saveClient(clientDTO, clientDTO.address());
        return ResponseEntity.ok().body(client);
    }


    @DeleteMapping("/delete-by-cpf")
    public ResponseEntity<String> deleteClientByCpf(@RequestParam String cpf) {
        try {
            clientService.deleteClient(cpf);
            return ResponseEntity.ok().body("Cliente desativado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> searchClientById(@PathVariable Long id) {
        Client client = clientService.searchClientById(id);
        ClientDTO clientDTO = clientMapperImpl.clientToClientDTO(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }

    @GetMapping("/sorted")
    public List<ClientDTO> getSortedClients() {
        return clientService.sortClientsByName();
    }
}
