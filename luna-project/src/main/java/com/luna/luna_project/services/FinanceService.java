package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinanceService {

    private final SchedulingService SchedulingService;
    private final ProductService ProductService;
    private final ProductStockRepository ProductStockRepository;

    private final SchedulingRepository schedulingRepository;

    public FinanceService(com.luna.luna_project.services.SchedulingService schedulingService, com.luna.luna_project.services.ProductService productService, com.luna.luna_project.repositories.ProductStockRepository productStockRepository, SchedulingRepository schedulingRepository) {
        SchedulingService = schedulingService;
        ProductService = productService;
        ProductStockRepository = productStockRepository;
        this.schedulingRepository = schedulingRepository;
    }

    
    public List <Double> formRevenueScheduleServices(LocalDate startDate, LocalDate endDate) {
        List <Double> revenueMontlyList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDateTime start =
                    LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);

            LocalDateTime end =
                    LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59, 0);

            double sumMontly = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end).stream().
                    flatMap(Scheduling -> Scheduling.getItems().stream()).
                    mapToDouble(Task::getValue).sum();
            revenueMontlyList.add(sumMontly);
        }
        return revenueMontlyList;
    }

    public List <Double> formRevenueScheduleProducts(LocalDate startDate, LocalDate endDate) {
        List <Double> revenueMontlyList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDateTime start =
                    LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);

            LocalDateTime end =
                    LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59, 0);

            double sumMonthly = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end).stream()
                    .flatMap(scheduling -> scheduling.getProducts().stream())
                    .map(product -> ProductStockRepository.findById(product.getId()))
                    .filter(Optional::isPresent) // Garantir que o Optional contém valor
                    .mapToDouble(optionalProductStock -> optionalProductStock.get().getPrice()) // Extrair o valor se presente
                    .sum();

            revenueMontlyList.add(sumMonthly);
        }
        return revenueMontlyList;
    }


    public Long formFrequencyScheduleServices(int days){
        LocalDateTime date = LocalDateTime.now();
        date.minusMonths(days);
        Long qttClients = schedulingRepository.countClientsWithoutRecentScheduling(date);
        return qttClients;
    }





}
