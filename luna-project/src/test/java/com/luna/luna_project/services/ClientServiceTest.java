package com.luna.luna_project.services;

import com.luna.luna_project.dtos.ResetPasswordDTO;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.models.Address;
import jakarta.validation.ValidationException;
import org.junit.Assert;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        Mockito.when(passwordEncoder.encode(client.getPassword())).thenReturn("encodedPassword");
        Mockito.when(clientRepository.save(client)).thenReturn(client);

        // Call the method under test
        Client savedClient = clientService.saveClient(client, addressDTO);

        // Assertions
        assertNotNull(savedClient);
        assertEquals("João Silva", savedClient.getName());
        assertEquals("123.456.789-00", savedClient.getCpf());
        assertTrue(savedClient.getRoles().contains("ROLE_USER"));
        assertTrue(savedClient.getRoles().contains("ROLE_ADMIN"));
        assertEquals("encodedPassword", savedClient.getPassword());
    }

    @DisplayName("Teste de Cadastro de cliente, com cliente com cpf já cadastrado")
    @Test
    void saveClientWithCPF409() {
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

        Mockito.when(clientRepository.existsByCpf("123.456.789-00")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clientService.saveClient(client, addressDTO);
        });
        assertEquals("409 CONFLICT \"CPF já cadastrado\"", exception.getMessage());
    }

    @DisplayName("Teste de Cadastro de cliente, com cliente com email já cadastrado")
    @Test
    void saveClientWithEmail409() {
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

        Mockito.when(clientRepository.existsByEmail("joao.silva@email.com")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clientService.saveClient(client, addressDTO);
        });
        assertEquals("409 CONFLICT \"Email já cadastrado\"", exception.getMessage());
    }

    @DisplayName("Teste listar clientes")
    @Test
    void searchClients(){
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

        List<Client> clients = List.of(
                Client.builder()
                        .id(1L)
                        .name("João Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                        .build(),
                Client.builder()
                        .id(1L)
                        .name("Jonas Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                        .build()
        );

        Mockito.when(clientRepository.findAll()).thenReturn(clients);


        assertEquals(clientService.searchClients(), clients);

    }
    @DisplayName("teste de procura de Employees")
    @Test
    public void searchEmployees() {
        Address address = Address.builder()
                .cep("06276-154")
                .logradouro("Praça da Paz")
                .number(123)
                .complemento("Casa 1")
                .cidade("São Paulo")
                .bairro("Vila Osasco")
                .uf("SP")
                .build();
        List<Client> clientsAdmin = List.of(
                Client.builder()
                        .id(1L)
                        .name("João Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                        .build(),
                Client.builder()
                        .id(1L)
                        .name("Jonas Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_ADMIN")))
                        .build()
        );

        List<Client> Employee = List.of(
                Client.builder()
                        .id(1L)
                        .name("João Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_EMPLOYEE", "ROLE_ADMIN")))
                        .build(),
                Client.builder()
                        .id(1L)
                        .name("Jonas Silva")
                        .cpf("123.456.789-00")
                        .email("joao.silva@email.com")
                        .phoneNumber("11987654321")
                        .password("senhaSegura123")
                        .birthDay(LocalDate.of(1990, 1, 1))
                        .address(address)
                        .roles(new HashSet<>(Set.of("ROLE_ADMIN","ROLE_EMPLOYEE")))
                        .build()
        );

        Mockito.when(clientRepository.findByRolesContaining("ROLE_ADMIN")).thenReturn(clientsAdmin);
        assertEquals(clientService.searchEmployees("ROLE_ADMIN"), clientsAdmin);
        Mockito.when(clientRepository.findByRolesContaining("ROLE_EMPLOYEE")).thenReturn(clientsAdmin);
        assertEquals(clientService.searchEmployees("ROLE_EMPLOYEE"), clientsAdmin);
    }
}