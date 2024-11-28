package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.ProductScheduling;
import com.luna.luna_project.models.ProductStock;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);

        int mounthStart= schedulings.get(0).getStartDateTime().getMonth().getValue();
        int mounthend= schedulings.get(schedulings.size()-1).getStartDateTime().getMonth().getValue();

        for (int i = mounthStart; i <= mounthend; i++) {
            int finalI = i;
            List<Scheduling> schedulingMounth = schedulings.stream()
                     .filter(scheduling -> scheduling.getStartDateTime().getMonth().getValue()== finalI).toList();

            double sumMontly =schedulingMounth.stream().
                    flatMap(Scheduling -> Scheduling.getItems().stream()).
                    mapToDouble(Task::getValue).sum();
            revenueMontlyList.add(sumMontly);
            start = start.plusMonths(1);
        }
        return revenueMontlyList;
    }

    public List <Double> formRevenueScheduleProducts(LocalDate startDate, LocalDate endDate) {

        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);
        List<ProductStock> productStockList =  ProductStockRepository.findAll();

        int mounthStart= schedulings.get(0).getStartDateTime().getMonth().getValue();
        int mounthend= schedulings.get(schedulings.size()-1).getStartDateTime().getMonth().getValue();

        List <Double> revenueMontlyList = new ArrayList<>();
        for (int i = mounthStart; i <= mounthend; i++) {
            int finalI = i;
            List<ProductScheduling> productsMonth = schedulings.stream()
                    .filter(scheduling -> scheduling.getStartDateTime().getMonthValue() == finalI)
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
