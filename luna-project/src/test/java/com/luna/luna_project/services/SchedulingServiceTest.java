package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {

    @Mock
    private  SchedulingRepository schedulingRepository;
    @Mock
    private  ProductStockRepository productStockRepository;

    @InjectMocks
    private SchedulingService schedulingService;



//    //// Método scheduleSave
//    @Test
//    @DisplayName("Teste de cadastro de agendamento Válido")
//    void schedulingValidSave() {
//
//        Address address = Address.builder()
//                .cep("06276-154")
//                .logradouro("Praça da Paz")
//                .number(123)
//                .complemento("Casa 1")
//                .cidade("São Paulo")
//                .bairro("Vila Osasco")
//                .uf("SP")
//                .build();
//
//
//        Client client = Client.builder()
//                .id(1L)
//                .name("João Silva")
//                .cpf("123.456.789-00")
//                .email("joao.silva@email.com")
//                .phoneNumber("11987654321")
//                .password("senhaSegura123")
//                .birthDay(LocalDate.of(1990, 1, 1))
//                .address(address)
//                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
//                .build();
//
//        Client employee = Client.builder()
//                .id(2L)
//                .name("João Silva")
//                .cpf("123.456.789-00")
//                .email("joao.silva@email.com")
//                .phoneNumber("11987654321")
//                .password("senhaSegura123")
//                .birthDay(LocalDate.of(1990, 1, 1))
//                .address(address)
//                .roles(new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN")))
//                .build();
//
//
//
//
//        Scheduling scheduling = Scheduling.builder()
//                .client(client)
//                .employee(employee)
//                .items(List.of(Task.valueOf("BOTOX")))
//                .startDateTime(LocalDateTime.of(2077, 1, 1,1,1))
//                .build();
//
//        List<LocalDateTime> listHours = List.of(
//                LocalDateTime.of(2077, 1, 1,1,1)
//        );
//
//        Mockito.when(schedulingRepository.findSchedulingByEmployee_IdAndStartDateTimeBetween(
//                Mockito.anyLong(), Mockito.any(), Mockito.any()
//        )).thenReturn(Collections.emptyList());
//
//        Mockito.when(schedulingRepository.findSchedulingByClient_IdAndStartDateTimeBetween(
//                Mockito.anyLong(), Mockito.any(), Mockito.any()
//        )).thenReturn(Collections.emptyList());
//
//
//        Mockito.when(schedulingService.listAvailable(scheduling.getEmployee().getId(),
//                scheduling.getClient().getId(),
//                scheduling.getStartDateTime(),
//                scheduling.calculateEndDate()
//        )).thenReturn(listHours);
//        Mockito.when(schedulingService.registerSchedule()).thenReturn(scheduling);
//        Mockito.when(schedulingService.validatyScheduleSave(scheduling)).thenReturn(true);
//        Scheduling scheduling1 = schedulingService.schedulingSave(scheduling);
//
//        assertNotNull(scheduling1);
//        assertEquals(scheduling1.getClient(), scheduling.getClient());
//        assertEquals(scheduling1.getStartDateTime(), scheduling.getStartDateTime());
//        assertEquals(scheduling1.getEmployee(), scheduling.getEmployee());
//    }
//


    //// Método addProducts
    @Test
    @DisplayName("Teste de add produtos válidos em um agendamento válido")
    void addProductsValidInScheduleValid() {
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




        Scheduling scheduling = Scheduling.builder()
                .id(1L)
                .client(client)
                .employee(employee)
                .products(new ArrayList<ProductScheduling>())
                .startDateTime(LocalDateTime.of(2077, 1, 1,1,1))
                .build();



        List<ProductScheduling> productSchedulingList = List.of(
                ProductScheduling.builder()
                        .id(1L)
                        .productName("Produto A")
                        .amount(10)
                        .price(10.0)
                        .build(),
                ProductScheduling.builder()
                        .id(2L)
                        .productName("Produto A")
                        .amount(10)
                        .price(10.0)
                        .build(),
                ProductScheduling.builder()
                        .id(3L)
                        .productName("Produto C")
                        .amount(5)
                        .price(10.0)
                        .build()
        );

        List<ProductStock> productStocks = List.of(
                ProductStock.builder()
                        .id(1L)
                        .name("Produto A")
                        .amount(10)
                        .price(10.0)
                        .build(),
                ProductStock.builder()
                        .id(2L)
                        .name("Produto A")
                        .amount(10)
                        .price(10.0)
                        .build(),
                ProductStock.builder()
                        .id(3L)
                        .name("Produto C")
                        .amount(5)
                        .price(10.0)
                        .build()
        );



        Scheduling schedulingReturn = Scheduling.builder()
                .id(1L)
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1, 1, 1))
                .products(productSchedulingList)
                .build();

        Mockito.when(schedulingRepository.save(scheduling)).thenReturn(scheduling);
        Mockito.when(schedulingRepository.findById(1L)).thenReturn(Optional.ofNullable(scheduling));
        Mockito.when(productStockRepository.findById(1L)).thenReturn(Optional.ofNullable(productStocks.get(0)));
        Mockito.when(productStockRepository.findById(2L)).thenReturn(Optional.ofNullable(productStocks.get(1)));
        Mockito.when(productStockRepository.findById(3L)).thenReturn(Optional.ofNullable(productStocks.get(2)));

        Scheduling schedulingTest =  schedulingService.addProducts(scheduling.getId(), productSchedulingList);

        assertNotNull(schedulingTest);
        assertEquals(schedulingTest.getProducts(), schedulingReturn.getProducts());
    }


    //// Método addProducts
    @Test
    @DisplayName("Teste de add produtos que já estão no agendamento")
    void addProductsInScheduleWithproducts() {
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
        Scheduling scheduling = Scheduling.builder()
                .id(1L)
                .client(client)
                .employee(employee)
                .products(productSchedulingList)
                .startDateTime(LocalDateTime.of(2077, 1, 1,1,1))
                .build();

        List<ProductScheduling> productSchedulingListTest = List.of(
                ProductScheduling.builder()
                        .id(1L)
                        .productName("Produto A")
                        .amount(20)
                        .build(),
                ProductScheduling.builder()
                        .id(2L)
                        .productName("Produto A")
                        .amount(20)
                        .build(),
                ProductScheduling.builder()
                        .id(3L)
                        .productName("Produto C")
                        .amount(10)
                        .build()
        );



        Scheduling schedulingReturn = Scheduling.builder()
                .id(1L)
                .client(client)
                .employee(employee)
                .startDateTime(LocalDateTime.of(2077, 1, 1, 1, 1))
                .products(productSchedulingListTest)
                .build();

        Mockito.when(schedulingRepository.save(scheduling)).thenReturn(scheduling);
        Mockito.when(schedulingRepository.findById(1L)).thenReturn(Optional.ofNullable(scheduling));

        Scheduling schedulingTest =  schedulingService.addProducts(scheduling.getId(), productSchedulingList);

        assertNotNull(schedulingTest);
        assertEquals(schedulingReturn.getProducts(), schedulingTest.getProducts());
    }

    //// Método addProducts
    @Test
    @DisplayName("Teste de add produtos, passado uma lista vazia")
    void addEmptyList() {
        List<ProductScheduling> productSchedulingList = List.of();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            schedulingService.addProducts(2L,productSchedulingList);
        });

        assertEquals("404 NOT_FOUND \"A lista passada está vazia\"", exception.getMessage());
    }


    @Test
    @DisplayName("Teste de add produtos, agendamento not found")
    void addProductsScheduleNotFound() {

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

        Mockito.when(schedulingRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            schedulingService.addProducts(2L,productSchedulingList);
        });

        assertEquals("404 NOT_FOUND \"Não existe agendamento com o id:2\"", exception.getMessage());
    }







}