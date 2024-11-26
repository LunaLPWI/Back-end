package com.luna.luna_project.services;

import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.models.Address;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste Service Cliente")
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ViaCepService viaCepService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    @DisplayName("Teste de Cadastro de cliente")
    @Test
    void saveClient() {
        // Mock AddressDTO
        AddressDTO addressDTO = AddressDTO.builder()
                .cep("06276-154")
                .logradouro("Praça da Paz")
                .number(123)
                .complemento("Casa 1")
                .cidade("São Paulo")
                .bairro("Vila Osasco")
                .uf("SP")
                .build();

        // Mock ClientRequestDTO
        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("João Silva")
                .cpf("123.456.789-00")
                .email("joao.silva@email.com")
                .phoneNumber("11987654321")
                .password("senhaSegura123")
                .birthDay(LocalDate.of(1990, 1, 1))
                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                .build();

        // Mock Address
        Address address = Address.builder()
                .cep("06276-154")
                .logradouro("Praça da Paz")
                .number(123)
                .complemento("Casa 1")
                .cidade("São Paulo")
                .bairro("Vila Osasco")
                .uf("SP")
                .build();

        // Mock Client
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .cpf("123.456.789-00")
                .email("joao.silva@email.com")
                .phoneNumber("11987654321")
                .password("senhaSegura123")
                .birthDay(LocalDate.of(1990, 1, 1))
                .address(address)
                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                .build();

        // Mock dependencies
        Mockito.when(viaCepService.saveAddress(addressDTO)).thenReturn(address);
        Mockito.when(clientMapper.clientRequestDTOtoClient(clientRequestDTO)).thenReturn(client);
        Mockito.when(passwordEncoder.encode(client.getPassword())).thenReturn("encodedPassword");
        Mockito.when(clientRepository.save(client)).thenReturn(client);

        // Call the method under test
        Client savedClient = clientService.saveClient(clientRequestDTO, addressDTO);

        // Assertions
        assertNotNull(savedClient);
        assertEquals("João Silva", savedClient.getName());
        assertEquals("123.456.789-00", savedClient.getCpf());
        assertTrue(savedClient.getRoles().contains("ROLE_USER"));
        assertTrue(savedClient.getRoles().contains("ROLE_ADMIN"));
        assertEquals("encodedPassword", savedClient.getPassword());
    }



}