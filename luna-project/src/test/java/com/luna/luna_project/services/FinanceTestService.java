package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.luna.luna_project.enums.Task.BARBA;
import static com.luna.luna_project.enums.Task.CORTE;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class FinanceTestService {

    @Mock
    private SchedulingRepository schedulingRepository;
    @Mock
    private ProductStockRepository productStockRepository;

    @InjectMocks
    private FinanceService financeService; // Initialize FinanceService

    @Test
    public void formRevenueScheduleServices(){

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
        List<Task> tasks = List.of(
                CORTE,BARBA
        );

        LocalDate start =
                LocalDate.of(2049, 1, 1);

        LocalDate end =
                LocalDate.of(2049, 12, 31);


        List<Scheduling> schedulingList = Arrays.asList(
                Scheduling.builder()
                        .id(1L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 1, 1,0,0))
                        .items(tasks)
                        .build(),
                Scheduling.builder()
                        .id(2L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 2, 1,0,0))
                        .items(List.of(CORTE))
                        .build(),
                Scheduling.builder()
                        .id(3L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 3, 1,0,0))
                        .items(tasks)
                        .build(),
                Scheduling.builder()
                        .id(4L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 4, 1,0,0))
                        .items(tasks)
                        .build()
        );


        Mockito.when(schedulingRepository.findSchedulingByStartDateTimeBetween(Mockito.any(), Mockito.any()))
                .thenReturn(schedulingList);

        List<Double> results = List.of(90.0, 50.0, 90.0, 90.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        assertEquals(financeService.formRevenueScheduleServicesValues(start,end),results);
    }



    @Test
    public void formRevenueScheduleProducts(){

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
        List<ProductStock> productStockList = Arrays.asList(
                ProductStock.builder()
                        .price(10.0)
                        .amount(2)
                        .id(1L)
                        .name("produto")
                        .build(),
                ProductStock.builder()
                        .price(10.0)
                        .amount(2)
                        .id(2L)
                        .name("produto")
                        .build(),
                ProductStock.builder()
                        .price(10.0)
                        .amount(2)
                        .id(3L)
                        .name("produto")
                        .build()
        );

        List<ProductScheduling> productSchedulings = Arrays.asList(
                ProductScheduling.builder()
                        .amount(2)
                        .id(1L)
                        .productName("produto")
                        .price(10.0)
                        .build(),
                ProductScheduling.builder()
                        .amount(2)
                        .id(2L)
                        .price(10.0)
                        .productName("produto")
                        .build()
        );

        LocalDate start =
                LocalDate.of(2049, 1, 1);

        LocalDate end =
                LocalDate.of(2049, 12, 31);


        List<Scheduling> schedulingList = Arrays.asList(
                Scheduling.builder()
                        .id(1L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 1, 1,0,0))
                        .products(productSchedulings)
                        .build(),
                Scheduling.builder()
                        .id(2L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 2, 1,0,0))
                        .products(productSchedulings)
                        .build(),
                Scheduling.builder()
                        .id(3L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 3, 1,0,0))
                        .products(productSchedulings)
                        .build(),
                Scheduling.builder()
                        .id(4L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 4, 1,0,0))
                        .products(productSchedulings)
                        .build()
        );


        Mockito.when(schedulingRepository.findSchedulingByStartDateTimeBetween(Mockito.any(), Mockito.any()))
                .thenReturn(schedulingList);
        Mockito.when(productStockRepository.findAll()).thenReturn(productStockList);

        List<Double> results = List.of(40.0, 40.0, 40.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(financeService.formRevenueScheduleProductsValues(start,end),results);
    }
//    @Test
//    public void formRevenueScheduleProductsQtt(){
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
//        List<ProductStock> productStockList = Arrays.asList(
//                ProductStock.builder()
//                        .price(10.0)
//                        .amount(2)
//                        .id(1L)
//                        .name("produto")
//                        .build(),
//                ProductStock.builder()
//                        .price(10.0)
//                        .amount(2)
//                        .id(2L)
//                        .name("produto")
//                        .build(),
//                ProductStock.builder()
//                        .price(10.0)
//                        .amount(2)
//                        .id(3L)
//                        .name("produto")
//                        .build()
//        );
//
//        List<ProductScheduling> productSchedulings = Arrays.asList(
//                ProductScheduling.builder()
//                        .amount(2)
//                        .id(1L)
//                        .productName("produto")
//                        .price(10.0)
//                        .build(),
//                ProductScheduling.builder()
//                        .amount(2)
//                        .id(2L)
//                        .price(10.0)
//                        .productName("produto")
//                        .build()
//        );
//
//        LocalDate start =
//                LocalDate.of(2049, 1, 1);
//
//        LocalDate end =
//                LocalDate.of(2049, 12, 31);
//
//
//        List<Scheduling> schedulingList = Arrays.asList(
//                Scheduling.builder()
//                        .id(1L)
//                        .client(client)
//                        .employee(employee)
//                        .startDateTime(LocalDateTime.of(2049, 1, 1,0,0))
//                        .products(productSchedulings)
//                        .build(),
//                Scheduling.builder()
//                        .id(2L)
//                        .client(client)
//                        .employee(employee)
//                        .startDateTime(LocalDateTime.of(2049, 2, 1,0,0))
//                        .products(productSchedulings)
//                        .build(),
//                Scheduling.builder()
//                        .id(3L)
//                        .client(client)
//                        .employee(employee)
//                        .startDateTime(LocalDateTime.of(2049, 3, 1,0,0))
//                        .products(productSchedulings)
//                        .build(),
//                Scheduling.builder()
//                        .id(4L)
//                        .client(client)
//                        .employee(employee)
//                        .startDateTime(LocalDateTime.of(2049, 4, 1,0,0))
//                        .products(productSchedulings)
//                        .build()
//        );
//
//
//        Mockito.when(schedulingRepository.findSchedulingByStartDateTimeBetween(Mockito.any(), Mockito.any()))
//                .thenReturn(schedulingList);
//        Mockito.when(productStockRepository.findAll()).thenReturn(productStockList);
//
//        List<Integer> results = List.of(4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0);
//        assertEquals(financeService.formRevenueScheduleProductsValues(start,end),results);
//    }

    @Test
    public void formRevenueScheduleServicesQtt(){

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
        List<Task> tasks = List.of(
                CORTE,BARBA
        );

        LocalDate start =
                LocalDate.of(2049, 1, 1);

        LocalDate end =
                LocalDate.of(2049, 12, 31);


        List<Scheduling> schedulingList = Arrays.asList(
                Scheduling.builder()
                        .id(1L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 1, 1,0,0))
                        .items(tasks)
                        .build(),
                Scheduling.builder()
                        .id(2L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 2, 1,0,0))
                        .items(List.of(CORTE))
                        .build(),
                Scheduling.builder()
                        .id(3L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 3, 1,0,0))
                        .items(tasks)
                        .build(),
                Scheduling.builder()
                        .id(4L)
                        .client(client)
                        .employee(employee)
                        .startDateTime(LocalDateTime.of(2049, 4, 1,0,0))
                        .items(tasks)
                        .build()
        );


        Mockito.when(schedulingRepository.findSchedulingByStartDateTimeBetween(Mockito.any(), Mockito.any()))
                .thenReturn(schedulingList);



        List<Integer> results = List.of(2, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0);

        assertEquals(financeService.formRevenueScheduleServicesQtt(start,end),results);
    }









}
