package com.luna.luna_project.services;

import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.ProductSchedulingRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {

    @Mock
    private  SchedulingRepository schedulingRepository;
    @Mock
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductSchedulingRepository productSchedulingRepository;
    @Mock
    private ProductStockRepository productStockRepository;



    @InjectMocks
    private SchedulingService schedulingService;

    @Test
    @DisplayName("Teste de cadastro de agendamento")
    void schedulingSave() {

        Address address = Address.builder()
                .cep("06276-154")
                .logradouro("Praça da Paz")
                .number(123)
                .complemento("Casa 1")
                .cidade("São Paulo")
                .bairro("Vila Osasco")
                .uf("SP")
                .build();


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

        Client employee = Client.builder()
                .id(2L)
                .name("João Silva")
                .cpf("123.456.789-00")
                .email("joao.silva@email.com")
                .phoneNumber("11987654321")
                .password("senhaSegura123")
                .birthDay(LocalDate.of(1990, 1, 1))
                .address(address)
                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                .build();

        clientRepository.save(client);
        clientRepository.save(employee);



        Scheduling scheduling = Scheduling.builder()
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1,1,1))
                .build();

        Scheduling schedulingTeste = Scheduling.builder()
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1,1,1))
                .build();


        Mockito.when(schedulingService.schedulingSave(scheduling)).thenReturn(schedulingTeste);

        Scheduling scheduling1 = schedulingService.schedulingSave(scheduling);

        assertNotNull(scheduling1);
        assertEquals(scheduling1.getClient(), scheduling.getClient());
        assertEquals(scheduling1.getStartDateTime(), scheduling.getStartDateTime());
        assertEquals(scheduling1.getEmployee(), scheduling.getEmployee());
    }

    @Test
    @DisplayName("Teste de add produtos")
    void addProducts() {
        // Criar um endereço
        Address address = Address.builder()
                .cep("06276-154")
                .logradouro("Praça da Paz")
                .number(123)
                .complemento("Casa 1")
                .cidade("São Paulo")
                .bairro("Vila Osasco")
                .uf("SP")
                .build();

        // Criar o cliente
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

        // Criar o funcionário
        Client employee = Client.builder()
                .id(2L)
                .name("João Silva")
                .cpf("123.456.789-00")
                .email("joao.silva@email.com")
                .phoneNumber("11987654321")
                .password("senhaSegura123")
                .birthDay(LocalDate.of(1990, 1, 1))
                .address(address)
                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
                .build();

        clientRepository.save(client);
        clientRepository.save(employee);

        Scheduling scheduling = Scheduling.builder()
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1, 1, 1))
                .build();
        schedulingRepository.save(scheduling);

        List<ProductScheduling> productSchedulingList = List.of(
                ProductScheduling.builder()
                        .id(1L)
                        .productName("Produto A")
                        .amount(10)
                        .build(),
                ProductScheduling.builder()
                        .id(2L)
                        .productName("Produto A")
                        .amount(10)
                        .build(),
                ProductScheduling.builder()
                        .id(3L)
                        .productName("Produto C")
                        .amount(5)
                        .build()
        );

        List<ProductStock> productStockList = List.of(
                ProductStock.builder()
                        .id(1L)
                        .name("Produto A")
                        .price(10.0)
                        .amount(10)
                        .build(),
                ProductStock.builder()
                        .id(2L)
                        .name("Produto A")
                        .price(10.0)
                        .amount(10)
                        .build(),
                ProductStock.builder()
                        .id(3L)
                        .name("Produto C")
                        .price(10.0)
                        .amount(5)
                        .build()
        );

        Scheduling schedulingReturn = Scheduling.builder()
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1, 1, 1))
                .products(productSchedulingList)
                .build();



        productStockRepository.saveAll(productStockList);
//        Mockito.when(productStockRepository.findAllById(Mockito.anyList())).thenReturn(productStockList);

        // Chamando o método que estamos testando
        Mockito.when(schedulingService.addProducts(scheduling.getId(), productSchedulingList)).thenReturn(schedulingReturn);

        // Verificando se o saveAll foi chamado no repositório de produtos
        Mockito.verify(productStockRepository).saveAll(Mockito.anyList());


        // Verificando se o save foi chamado no repositório de agendamento
        Mockito.verify(schedulingRepository).save(Mockito.any(Scheduling.class));
    }

}