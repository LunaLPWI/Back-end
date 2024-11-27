package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.ClientRepository;
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

    @InjectMocks
    private FinanceService financeService; // Initialize FinanceService
//////QUEBRADO PROVAVELMENTE  O MÈTODO TBM
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

        LocalDateTime startTime =
                LocalDateTime.of(2049, 1, 1, 0, 0, 0);

        LocalDateTime endTime =
                LocalDateTime.of(2049, 12, 31, 23, 59, 0);

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


        Mockito.when(schedulingRepository.findSchedulingByStartDateTimeBetween(startTime, endTime))
                .thenReturn(schedulingList);

        List<Double> results = List.of(90.0,30.0,90.0,90.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);

        assertEquals(financeService.formRevenueScheduleServices(start,end),results);
    }




}
