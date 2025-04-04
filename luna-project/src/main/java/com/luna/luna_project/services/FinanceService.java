package com.luna.luna_project.services;

import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class FinanceService {

    private final ProductStockRepository ProductStockRepository;
    private final FrenquencyDTO frenquencyDTO;

    private final SchedulingRepository schedulingRepository;
    private final PlanRepository planRepository;

    public FinanceService(com.luna.luna_project.repositories.ProductStockRepository productStockRepository, FrenquencyDTO frenquencyDTO, SchedulingRepository schedulingRepository, PlanRepository planRepository) {
        ProductStockRepository = productStockRepository;
        this.frenquencyDTO = frenquencyDTO;
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
                    mapToDouble(Task::getValue).sum();
            revenueMontlyList.add(sumMontly);
            start = start.plusMonths(1);
            time = time.plusMonths(1);
        }
        return revenueMontlyList;
    }

    public List <Double> formRevenueScheduleProductsValues(LocalDate startDate, LocalDate endDate) {

        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);

        List<ProductStock> productStockList =  ProductStockRepository.findAll();
        LocalDateTime time = start;

        List <Double> revenueMontlyList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            LocalDateTime finalTime = time;
            List<ProductScheduling> productsMonth = schedulings.stream()
                    .filter(scheduling -> scheduling.getStartDateTime().getMonth() == finalTime.getMonth()
                            && scheduling.getStartDateTime().getYear() == finalTime.getYear())
                    .flatMap(scheduling -> scheduling.getProducts().stream())
                    .toList();

            double sumMonthly = productsMonth.stream()
                    .filter(productScheduling ->
                            productStockList.stream()
                            .map(ProductStock::getId).
                                    anyMatch(id -> id.equals(productScheduling.getId()))
                    )
                    .mapToDouble(productStock ->
                            productStock.getAmount() * productStock.getPrice())
                    .sum();
            time = time.plusMonths(1);
            revenueMontlyList.add(sumMonthly);
        }
        return revenueMontlyList;
    }

    public List <Integer> formRevenuePlanQtt(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List <Plan> plans = planRepository.findPlanByCreatedAtBetween(start, end);

        LocalDateTime time = start;
        List <Integer> revenueMontlyList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {

            LocalDateTime finalTime = time;
            List<Plan> productsMonth = plans.stream()
                    .filter(plan -> plan.getCreated_at().getMonth() == finalTime.getMonth()
                            && plan.getCreated_at().getYear() == finalTime.getYear()).toList();


            time = time.plusMonths(1);
            revenueMontlyList.add(productsMonth.size());
        }
        return revenueMontlyList;
    }

    public List <Integer> formRevenueScheduleServicesQtt(LocalDate startDate, LocalDate endDate) {
        List <Integer> revenueMontlyList = new ArrayList<>();
        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);

        LocalDateTime time = start;
        for (int i = 1; i <= 12; i++) {
            LocalDateTime finalTime = time;
            List<Scheduling> schedulingMounth = schedulings.stream()
                    .filter(scheduling -> scheduling.getStartDateTime().getMonth() == finalTime.getMonth()
                            && scheduling.getStartDateTime().getYear() == finalTime.getYear()).toList();

            int sumMontly =schedulingMounth.stream().
                    flatMap(Scheduling -> Scheduling.getItems().stream()).toList().size();


            revenueMontlyList.add(sumMontly);
            start = start.plusMonths(1);
            time = time.plusMonths(1);
        }
        return revenueMontlyList;
    }

    public Long getProductQttforEmployee(LocalDateTime startDate, LocalDateTime endDate, Long id) {
        Long num = schedulingRepository.sumProductAmountsByEmployeeAndDateRange(id,startDate, endDate);

        if(num == null){
            num = 0L;
        }
        return num;
    }

    public Long getServiceQttforEmployee(LocalDateTime startDate, LocalDateTime endDate, Long id) {
        Long num = schedulingRepository.sumServicesByEmployeeAndDateRange(id,startDate, endDate);
        if(num == null){
            num = 0L;
        }
        return num;
    }

    public FrenquencyDTO formFrequencyScheduleServices() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(15);


        // Frequentes: 16/11/2024 - 01/12/2024
        List<Client> frequentes = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate,"ROLE_EMPLOYEE");
        startDate = endDate.minusDays(15);  
        endDate = startDate.minusDays(15);
        List<Client> medios = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate,"ROLE_EMPLOYEE");
        System.out.println("Médios: " + medios); // Log para depuração
        startDate = endDate.minusDays(15);
        endDate = startDate.minusDays(120);
        List<Client> ocasionais = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate,"ROLE_EMPLOYEE");
        System.out.println("Ocasionais: " + ocasionais); // Log para depuração

        frenquencyDTO.setOcasionais(ocasionais.size());
        frenquencyDTO.setMedios(medios.size());
        frenquencyDTO.setFrequentes(frequentes.size());


        return frenquencyDTO;
    }

    public String formFrequencyScheduleServiceById(Long id) {
        // Frequentes: 16/11/2024 - 01/12/2024
        Scheduling frequencyOpp = schedulingRepository.findLastSchedulingByClientId(id);

        if (frequencyOpp == null) {
            return "Sem agendamentos encontrados";
        }
        if (frequencyOpp.getStartDateTime().isBefore(LocalDateTime.now())
                && frequencyOpp.getStartDateTime().isAfter(LocalDateTime.now().minusDays(15))) {
            return "Frequente";
        } else if (frequencyOpp.getStartDateTime().isBefore(LocalDateTime.now().minusDays(15))
                && frequencyOpp.getStartDateTime().isAfter(LocalDateTime.now().minusDays(30))) {
            return "Médio";
        } else {
            return "Ocasional";
        }
    }

}
