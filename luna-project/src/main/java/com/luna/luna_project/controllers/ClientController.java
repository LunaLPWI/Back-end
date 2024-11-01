package com.luna.luna_project.controllers;

import com.luna.luna_project.GerenciadorTokenJwt;
import com.luna.luna_project.dtos.client.ClientLoginDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.repositories.ClientMapper;
import com.luna.luna_project.services.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private ClientMapper clientMapper;
//    @Autowired
//    private GerenciadorTokenJwt gerenciadorTokenJwt;


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
        return ResponseEntity.ok().body(client);
    }


    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody @Valid ClientRequestDTO clientDTO) {
        Client client = clientService.saveClient(clientDTO, clientDTO.getAddress());
        ClientResponseDTO clientResponseDTO = clientMapper.clientToClientDTOResponse(client);
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
    @GetMapping("/search-by-name")
    public ResponseEntity<ClientRequestDTO> searchClientById(@RequestParam String nome) {
        Client client = clientService.searchByUsername(nome);
        ClientRequestDTO clientDTO = clientMapper.clientToClientDTORequest(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientRequestDTO> searchClientById(@PathVariable Long id) {
        Client client = clientService.searchClientById(id);
        ClientRequestDTO clientDTO = clientMapper.clientToClientDTORequest(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }

    @GetMapping("/sorted")
    public List<ClientResponseDTO> getSortedClients() {
        return clientService.sortClientsByName();
    }


    @GetMapping("/search-by-email")
    public ResponseEntity<Long> searchClientByEmail(@RequestParam String email) {
        Client client = clientService.searchClientByEmail(email);
        return ResponseEntity.ok().body(client.getId());
    }


    @PostMapping("/login")
    public ResponseEntity<ClientTokenDTO> login(@RequestBody ClientLoginDTO usuarioLoginDto) {
        ClientTokenDTO usuarioTokenDto = this.clientService.authenticate(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        try {
            clientService.resetPassword(email, newPassword);
            return ResponseEntity.ok().body("Senha alterada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
