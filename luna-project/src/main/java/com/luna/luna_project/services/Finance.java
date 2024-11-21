package com.luna.luna_project.services;

import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Finance {

    private final SchedulingService SchedulingService;
    private final ProductService ProductService;

    private final SchedulingRepository schedulingRepository;

    public Finance(com.luna.luna_project.services.SchedulingService schedulingService, com.luna.luna_project.services.ProductService productService, SchedulingRepository schedulingRepository) {
        SchedulingService = schedulingService;
        ProductService = productService;
        this.schedulingRepository = schedulingRepository;
    }

    
    public List <Double> formRevenueScheduleServicesMontly(){
        List <Double> revenueMontlyList = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 12; i++) {
            LocalDateTime start =
                    LocalDateTime.of(date.getYear(), date.getMonth().minus(1), 1, 0, 0, 0);

            LocalDateTime end =
                    LocalDateTime.of(LocalDateTime.now().getYear(), date.getMonth(), 30, 23, 59, 59);

            double sumMontly = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end).stream().
                    flatMap(Scheduling -> Scheduling.getItems().stream()).
                    mapToDouble(Task::getValue).sum();
            revenueMontlyList.add(sumMontly);
            date = date.minusMonths(1);
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
