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

//    @PostMapping("/request-password-reset")
//    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
//        Client client = clientService.searchClientByEmail(email);
//        if (client == null) {
//            return ResponseEntity.status(404).body("Cliente não encontrado.");
//        }
//
//        String token = gerenciadorTokenJwt.generatePasswordResetToken(client.getNome());
//
//
//
//
//        return ResponseEntity.ok("E-mail de redefinição de senha enviado.");
//    }
//
//    @PatchMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        Claims claims;
//        try {
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(gerenciadorTokenJwt.parseSecret())
//                    .build()
//                    .parseClaimsJws(token).getBody();
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body("Token inválido ou expirado.");
//        }
//
//        String username = claims.getSubject();
//        Client client = clientService.searchByUsername(username);
//        if (client == null) {
//            return ResponseEntity.status(404).body("Cliente não encontrado.");
//        }
//
//        clientService.redefinePassword(client.getId(), newPassword);
//        return ResponseEntity.ok("Senha redefinida com sucesso.");
//    }

    @GetMapping("/search-by-email")
    public ResponseEntity<Long> searchClientByEmail(@RequestParam String email) {
        Client client = clientService.searchClientByEmail(email);
        if (client == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(client.getId());
    }

//    @GetMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestParam String id,String) {
//        Client client = clientService.redefinePassword();
//        if (client == null) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok().body("Senha alterada com sucesso");
//    }

    @PostMapping("/login")
    public ResponseEntity<ClientTokenDTO> login(@RequestBody ClientLoginDTO usuarioLoginDto) {
            ClientTokenDTO usuarioTokenDto = this.clientService.authenticate(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
