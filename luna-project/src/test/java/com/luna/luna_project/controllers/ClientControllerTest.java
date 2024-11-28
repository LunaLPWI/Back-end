package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.client.ClientLoginDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.services.ClientService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllClients() {
        // Dados mockados
        List<ClientResponseDTO> mockClients = List.of(
                new ClientResponseDTO(1L, "John Doe", "john@example.com", "123456789", LocalDate.of(1990, 5, 15), Set.of("USER"))
        );

        when(clientService.searchClients()).thenReturn(mockClients);

        // Execução do método
        ResponseEntity<List<ClientResponseDTO>> response = clientController.searchClients();

        // Verificações
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(clientService, times(1)).searchClients();
    }

    @Test
    void shouldFindClientByCpf() {
        String cpf = "12345678900";
        Client mockClient = new Client(1L, "John Doe", cpf, "john@example.com", "password123", LocalDate.of(1990, 5, 15), "123456789",
                null, Set.of("USER"));
        ClientResponseDTO expectedResponse = new ClientResponseDTO(1L, "John Doe", "john@example.com", "123456789",
                LocalDate.of(1990, 5, 15), Set.of("USER"));

        when(clientService.searchClientByCpf(cpf)).thenReturn(mockClient);
        when(clientMapper.clientToClientDTOResponse(mockClient)).thenReturn(expectedResponse);

        ResponseEntity<ClientResponseDTO> response = clientController.searchClientByCpf(cpf);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(clientService, times(1)).searchClientByCpf(cpf);
    }

    @Test
    void shouldSaveClientSuccessfully() {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO(
                1L, "John Doe", "12345678900", "john@example.com", "123456789", "password123",
                new AddressDTO("03651230", "Rua 1", 10, "casa 12", "12345-678", "Cidade", "UF"), LocalDate.of(1990, 5, 15), Set.of("USER")
        );

        Client mockClient = new Client(1L, "John Doe", "12345678900", "john@example.com", "password123",
                LocalDate.of(1990, 5, 15), "123456789", null, Set.of("USER"));

        ClientResponseDTO responseDTO = new ClientResponseDTO(1L, "John Doe", "john@example.com", "123456789",
                LocalDate.of(1990, 5, 15), Set.of("USER"));

        when(clientService.saveClient(any(ClientRequestDTO.class), any())).thenReturn(mockClient);
        when(clientMapper.clientToClientDTOResponse(mockClient)).thenReturn(responseDTO);

        ResponseEntity<ClientResponseDTO> response = clientController.saveClient(clientRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDTO, response.getBody());
        verify(clientService, times(1)).saveClient(any(ClientRequestDTO.class), any());
    }

    @Test
    void shouldDeleteClientByCpf() {
        String cpf = "12345678900";

        ResponseEntity<String> response = clientController.deleteClientByCpf(cpf);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cliente desativado com sucesso!", response.getBody());
        verify(clientService, times(1)).deleteClient(cpf);
    }

    @Test
    void shouldThrowErrorWhenDeletingNonExistentClient() {
        String cpf = "12345678900";
        doThrow(new ValidationException("Cliente não encontrado")).when(clientService).deleteClient(cpf);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clientController.deleteClientByCpf(cpf);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clientService, times(1)).deleteClient(cpf);
    }


    @Test
    void shouldAuthenticateClient() {
        ClientLoginDTO loginDTO = new ClientLoginDTO("john@example.com", "password123");
        ClientTokenDTO tokenDTO = new ClientTokenDTO(1L, "John Doe", "john@example.com", "jwt-token", null,
                LocalDate.of(1990, 5, 15), "123456789", Set.of("USER"));

        when(clientService.authenticate(loginDTO)).thenReturn(tokenDTO);

        ResponseEntity<ClientTokenDTO> response = clientController.login(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        verify(clientService, times(1)).authenticate(loginDTO);
    }
}
