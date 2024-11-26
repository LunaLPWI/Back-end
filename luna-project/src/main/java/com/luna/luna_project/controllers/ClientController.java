package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.ResetPasswordDTO;
import com.luna.luna_project.dtos.client.ClientLoginDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.services.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_EMPLOYEE")
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> searchClients() {
        List<ClientResponseDTO> clients = clientService.searchClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/search-employee")
    public ResponseEntity<List<ClientResponseDTO>> searchEmployee(@RequestParam String role) {
        List<Client> employesEntity = clientService.searchEmployees(role);
        if (employesEntity.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ClientResponseDTO> employes = employesEntity.stream()
                .map(clientMapper::clientToClientDTOResponse).toList();
        return ResponseEntity.ok().body(employes);
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/search-by-cpf")
    public ResponseEntity<ClientResponseDTO> searchClientByCpf(@RequestParam String cpf) {
        Client client = clientService.searchClientByCpf(cpf);
        return ResponseEntity.ok().body(clientMapper.clientToClientDTOResponse(client));
    }


    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody @Valid ClientRequestDTO clientDTO) {
        Client client = clientService.saveClient(clientMapper.clientRequestDTOtoClient(clientDTO),
                clientDTO.getAddress());
        ClientResponseDTO clientResponseDTO = clientMapper.clientToClientDTOResponse(client);
        return ResponseEntity.ok().body(clientResponseDTO);
    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete-by-cpf")
    public ResponseEntity<String> deleteClientByCpf(@RequestParam String cpf) {
        try {
            clientService.deleteClient(cpf);
            return ResponseEntity.ok().body("Cliente desativado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/search-by-name")
    public ResponseEntity<ClientRequestDTO> searchClientById(@RequestParam String nome) {
        Client client = clientService.searchByUsername(nome);
        ClientRequestDTO clientDTO = clientMapper.clientToClientDTORequest(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/{id}")
    public ResponseEntity<ClientRequestDTO> searchClientById(@PathVariable Long id) {
        Client client = clientService.searchClientById(id);
        ClientRequestDTO clientDTO = clientMapper.clientToClientDTORequest(client);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(clientDTO);
    }
    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/sorted")
    public List<ClientResponseDTO> getSortedClients() {
        return clientService.sortClientsByName();
    }

    @SecurityRequirement(name = "Baerer")
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
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            clientService.resetPassword(resetPasswordDTO);
            return ResponseEntity.ok().body("Senha alterada com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
