package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.services.FinanceService;
import com.luna.luna_project.services.SchedulingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/finance")


public class FinanceController {

    private final FinanceService financeService;


    public FinanceController(SchedulingService schedulingService, FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/revenue/services")
    public List<Double> revenueServices(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleServicesValues(startDate,endDate);
    }
    @GetMapping("/revenue/products")
    public List<Double> revenueProducts(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleProductsValues(startDate,endDate);
    }
    @GetMapping("/revenue/frequence")
    public FrenquencyDTO frequence(){
        return financeService.formFrequencyScheduleServices();
    }

    @GetMapping("/quantity/services")
    public long qttQuantityServices(@RequestParam LocalDate startDate,
                                    @RequestParam LocalDate endDate, @RequestParam Long id){
        LocalDateTime start = LocalDateTime.of(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth(),0,0,0);
        LocalDateTime end = LocalDateTime.of(endDate.getYear(),endDate.getMonth(),endDate.getDayOfMonth(),23,59,0);
        return financeService.getServiceQttforEmployee(start, end, id);
    }

    @GetMapping("/quantity/products")
    public long qttQuantityProducts(@RequestParam LocalDate startDate,
                                    @RequestParam LocalDate  endDate, @RequestParam Long id){

        LocalDateTime start = LocalDateTime.of(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth(),0,0,0);
        LocalDateTime end = LocalDateTime.of(endDate.getYear(),endDate.getMonth(),endDate.getDayOfMonth(),23,59,0);
        return financeService.getProductQttforEmployee(start, end, id);
    }
}
