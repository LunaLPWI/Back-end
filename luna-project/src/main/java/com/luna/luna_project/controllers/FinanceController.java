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
    /////!!!!!!!!!!!ATENÇÃO!!!!!!!!!!!!!

    // retorna os valores dos serviços do primeiro gráfico de linhas, referente ao ano, então dependendo da data de inicio e da data de fim
    //dependendo da data que colocar ele muda as posições da lista, mas a lista sempre retornará 12 valores,
    //por exemplo se começar a partir de janeiro e ir até dezembro ele retornará os valores nas posições certas,
    //se colocar a partir de fevereiro a dezembro, ele colocará o valor de fevereiro em janeiro e o de abril em março e
    //assim em diante, se colocar a partir de março ele pulará 2 meses para trás, ENTÃO para trazer valores nas posições certas


    ///SEMPRE MANDE UM ANO INTEIRO DE JANEIRO A DEZEMBRO, DE MARÇO a FEVEREIRO e assim em diante, completando sempre
    /// ao todo  12 meses
    @GetMapping ("/revenue/services")
    public List<Double> revenueServices(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleServicesValues(startDate,endDate);
    }

    //mesma coisa do anterior porem para valor de produtos
    @GetMapping("/revenue/products")
    public List<Double> revenueProducts(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleProductsValues(startDate,endDate);
    }

    //mesma coisa do anterior porem para quantidade de serviços feitos
    @GetMapping("/quantity/services")
    public List<Integer> revenueServicesLineQTT(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleServicesQtt(startDate,endDate);
    }
    //mesma coisa do anterior porem para quantidade de produtos vendidos
    @GetMapping("/quantity/products")
    public List<Integer> revenueProductsLineQTT(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return financeService.formRevenueScheduleProductsQtt(startDate,endDate);
    }




    ///esses retornam dados únicos só mandar as datas referentes que tá suave, data de inicio fim e o id do funcionário



    //Retorna a a quantidade de serviços dado o funcionário
    @GetMapping("/quantity/services-employee")
    public long qttQuantityServices(@RequestParam LocalDate startDate,
                                    @RequestParam LocalDate endDate, @RequestParam Long funcId){
        LocalDateTime start = LocalDateTime.of(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth(),0,0,0);
        LocalDateTime end = LocalDateTime.of(endDate.getYear(),endDate.getMonth(),endDate.getDayOfMonth(),23,59,0);
        return financeService.getServiceQttforEmployee(start, end, funcId);
    }

    //Retorna a a quantidade de produtos dado o funcionário de uma data a outra
    @GetMapping("/quantity/products-employee")
    public long qttQuantityProducts(@RequestParam LocalDate startDate,
                                    @RequestParam LocalDate  endDate, @RequestParam Long funcId){

        LocalDateTime start = LocalDateTime.of(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth(),0,0,0);
        LocalDateTime end = LocalDateTime.of(endDate.getYear(),endDate.getMonth(),endDate.getDayOfMonth(),23,59,0);
        return financeService.getProductQttforEmployee(start, end, funcId);
    }

    //Retorna a frequencia dos clientes
    @GetMapping("/revenue/frequence")
    public FrenquencyDTO frequence(){
        return financeService.formFrequencyScheduleServices();
    }
}
