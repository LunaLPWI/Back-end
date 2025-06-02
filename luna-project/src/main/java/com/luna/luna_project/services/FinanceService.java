package com.luna.luna_project.services;

import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.mockito.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;


@Service
public class FinanceService {


    private final FrenquencyDTO frenquencyDTO;
    private final ClientRepository clientRepository;
    private final SchedulingRepository schedulingRepository;
    private final PlanRepository planRepository;

    public FinanceService(
            FrenquencyDTO frenquencyDTO, ClientRepository clientRepository, SchedulingRepository schedulingRepository,
            PlanRepository planRepository

    ) {

        this.frenquencyDTO = frenquencyDTO;
        this.clientRepository = clientRepository;
        this.schedulingRepository = schedulingRepository;
        this.planRepository = planRepository;
    }

    
    public List <Double> formRevenueScheduleServicesValues(LocalDate startDate, LocalDate endDate) {
        List <Double> revenueMontlyList = new ArrayList<>();
        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);
        schedulings.forEach(s -> System.out.println(s));
        LocalDateTime time = start;
        for (int i = 1; i <= 12; i++) {
            LocalDateTime finalTime = time;
            List<Scheduling> schedulingMounth = schedulings.stream()
                     .filter(scheduling -> scheduling.getStartDateTime().getMonth() == finalTime.getMonth()
                             && scheduling.getStartDateTime().getYear() == finalTime.getYear()).toList();

            double sumMontly =schedulingMounth.stream().
                    flatMap(Scheduling -> Scheduling.getItems().stream()).
                    mapToDouble(EmployeeTask::getValue).sum();
            revenueMontlyList.add(sumMontly);
            start = start.plusMonths(1);
            time = time.plusMonths(1);
        }
        return revenueMontlyList;
    }



    public List <Long> formRevenueScheduleServicesQtt(Long establishmentId) {

        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0, 0);
        LocalDateTime startDateTime = endDateTime.with(TemporalAdjusters.lastDayOfMonth());

        List<Long> monthlyTotals = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            monthlyTotals.add(schedulingRepository.countTotalServicesByEstablishmentAndTimeRange(establishmentId, startDateTime, endDateTime));
            startDateTime = startDateTime.minusMonths(1);
            endDateTime = endDateTime.minusMonths(1);
        }
        return monthlyTotals;
    }


    public Long getServiceQttforEmployee(LocalDateTime startDate, LocalDateTime endDate, Long id) {
        Long num = schedulingRepository.sumServicesByEmployeeAndDateRange(id,startDate, endDate);
        if(num == null){
            num = 0L;
        }
        return num;
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
