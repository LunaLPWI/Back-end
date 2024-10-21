package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.mapper.ClientMapper;
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



    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> searchClients() {
        List<ClientResponseDTO> clients = clientService.searchClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(clients);
    }


    @GetMapping("/search-by-cpf")
    public ResponseEntity<ClientResponseDTO> searchClientByCpf(@RequestParam String cpf) {
        ClientResponseDTO client = clientService.searchClientByCpf(cpf);
        if (client == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(client);
    }


    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody @Valid ClientRequestDTO clientDTO) {
        System.out.println(clientDTO);
        if (clientService.existsCpf(clientDTO.getCpf())) {
            return ResponseEntity.status(409).build();
        }
        Client client = clientService.saveClient(clientDTO, clientDTO.getAddress());
        ClientResponseDTO clientResponseDTO = ClientMapper.clientToClientDTOResponse(client);
        return ResponseEntity.ok().body(clientResponseDTO);
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
    public ResponseEntity<ClientRequestDTO> searchClientById(@PathVariable Long id) {
        Client client = clientService.searchClientById(id);
        ClientRequestDTO clientDTO = ClientMapper.clientToClientDTORequest(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }

    @GetMapping("/sorted")
    public List<ClientResponseDTO> getSortedClients() {
        return clientService.sortClientsByName();
    }

    @PatchMapping("/redefine-password")
    public ResponseEntity<ClientResponseDTO> redefinePassword(@RequestParam Long id,  @RequestParam String password) {
       Client client =  clientService.redefinePassword(id, password);
       return ResponseEntity.ok(ClientMapper.clientToClientDTOResponse(client));
    }
}
