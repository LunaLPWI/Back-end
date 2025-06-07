package com.luna.luna_project.services;

import com.luna.luna_project.dtos.EmployeeServiceCount;
import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.EstablishmentRepository;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.mockito.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FinanceService {


    private final FrenquencyDTO frenquencyDTO;
    private final ClientRepository clientRepository;
    private final SchedulingRepository schedulingRepository;
    private final PlanRepository planRepository;
    private final EstablishmentRepository establishmentRepository;

    public FinanceService(
            FrenquencyDTO frenquencyDTO, ClientRepository clientRepository, SchedulingRepository schedulingRepository,
            PlanRepository planRepository, EstablishmentRepository establishmentRepository

    ) {

        this.frenquencyDTO = frenquencyDTO;
        this.clientRepository = clientRepository;
        this.schedulingRepository = schedulingRepository;
        this.planRepository = planRepository;
        this.establishmentRepository = establishmentRepository;
    }

    
    public List <Double> formRevenueScheduleServicesValues(Long establishmentId) {
        Optional<Establishment> establishment = establishmentRepository.findById(establishmentId);
        if (establishment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    " Estabelecimento de id: %d não encontrado".formatted(establishmentId));
        }
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = LocalDateTime.of(today.getYear(), today.getMonth(), 1, 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        List<Double> monthlyTotals = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Double total = schedulingRepository
                    .sumTotalServiceValuesByEstablishmentAndTimeRange(establishmentId, startDateTime, endDateTime);
            monthlyTotals.add(total != null ? total : 0L);

            startDateTime = startDateTime.minusMonths(1);
            endDateTime = endDateTime.minusMonths(1);
        }
        return monthlyTotals;
    }



    public List<Long> formRevenueScheduleServicesQtt(Long establishmentId) {
        Optional<Establishment> establishment = establishmentRepository.findById(establishmentId);
        if (establishment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    " Estabelecimento de id: %d não encontrado".formatted(establishmentId));
        }
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = LocalDateTime.of(today.getYear(), today.getMonth(), 1, 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        List<Long> monthlyTotals = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Long total = schedulingRepository
                    .countTotalServicesByEstablishmentAndTimeRange(establishmentId, startDateTime, endDateTime);
            monthlyTotals.add(total != null ? total : 0L);

            startDateTime = startDateTime.minusMonths(1);
            endDateTime = endDateTime.minusMonths(1);
        }
        return monthlyTotals;
    }


    public List<EmployeeServiceCount> getServicesPerEmployeeLast30Days(Long establishmentId) {
        LocalDateTime now   = LocalDateTime.now();
        LocalDateTime start = now.minusDays(30);
        return schedulingRepository
                .countServicesByEmployeeInPeriod(establishmentId, start, now);
    }

    public FrenquencyDTO formFrequencyScheduleServices(Long stablishmentId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(3);
        // Inicializa o DTO
        FrenquencyDTO frenquencyDTO = new FrenquencyDTO();
        // Frequentes, Médios e Ocasional
        Long frequentes = clientRepository.getFrequencyClientsClientsByEstablishmentAndRole(startDate, endDate, stablishmentId, "ROLE_EMPLOYEE", 999);
        Long medios = clientRepository.getFrequencyClientsClientsByEstablishmentAndRole(startDate, endDate, stablishmentId, "ROLE_EMPLOYEE", 4);
        Long ocasionais = clientRepository.getFrequencyClientsClientsByEstablishmentAndRole(startDate, endDate, stablishmentId, "ROLE_EMPLOYEE", 2);
        // Verifica se os valores são nulos e inicializa com 0 se necessário
        frenquencyDTO.setFrequentes(frequentes != null ? frequentes : 0);
        frenquencyDTO.setMedios(medios != null ? medios : 0);
        frenquencyDTO.setOcasionais(ocasionais != null ? ocasionais : 0);
        return frenquencyDTO;
    }
}
