package com.luna.luna_project.services;

import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.ProductScheduling;
import com.luna.luna_project.models.ProductStock;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final FrenquencyDTO frenquencyDTO;

    private final SchedulingRepository schedulingRepository;

    public FinanceService(com.luna.luna_project.services.SchedulingService schedulingService, com.luna.luna_project.services.ProductService productService, com.luna.luna_project.repositories.ProductStockRepository productStockRepository, FrenquencyDTO frenquencyDTO, SchedulingRepository schedulingRepository) {
        SchedulingService = schedulingService;
        ProductService = productService;
        ProductStockRepository = productStockRepository;
        this.frenquencyDTO = frenquencyDTO;
        this.schedulingRepository = schedulingRepository;
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

    public List <Integer> formRevenueScheduleProductsQtt(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start =
                LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end =
                LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0, 0);

        List < Scheduling> schedulings = schedulingRepository.findSchedulingByStartDateTimeBetween(start, end);
        List<ProductStock> productStockList =  ProductStockRepository.findAll();

        LocalDateTime time = start;
        List <Integer> revenueMontlyList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {

            LocalDateTime finalTime = time;
            List<ProductScheduling> productsMonth = schedulings.stream()
                    .filter(scheduling -> scheduling.getStartDateTime().getMonth() == finalTime.getMonth()
                            && scheduling.getStartDateTime().getYear() == finalTime.getYear())
                    .flatMap(scheduling -> scheduling.getProducts().stream())
                    .toList();

            int sumMonthly = productsMonth.stream()
                    .filter(productScheduling -> productStockList.stream()
                            .anyMatch(productStock -> productStock.getId().equals(productScheduling.getId())))
                    .mapToInt(ProductScheduling::getAmount)
                    .sum();
            time = time.plusMonths(1);
            revenueMontlyList.add(sumMonthly);
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
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há produtos veinculados a esse funcionário");
        }
        return num;
    }

    public Long getServiceQttforEmployee(LocalDateTime startDate, LocalDateTime endDate, Long id) {
        Long num = schedulingRepository.sumServicesByEmployeeAndDateRange(id,startDate, endDate);
        if(num == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há serviços veinculados a esse funcionário");
        }
        return num;
    }

    public FrenquencyDTO formFrequencyScheduleServices() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(15);
        List<Long> numbers = new ArrayList<>();

        // Frequentes: 16/11/2024 - 01/12/2024
        List<Client> frequentes = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate,"ROLE_EMPLOYEE");
        numbers.add((long)frequentes.size());
        System.out.println("Frequentes: " + frequentes); // Log para depuração

        // Médios: 02/11/2024 - 16/11/2024
        startDate = endDate;
        endDate = startDate.minusDays(15);
        List<Client> medios = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate, "ROLE_EMPLOYEE");
        numbers.add((long)medios.size());
        System.out.println("Médios: " + medios); // Log para depuração

        // Ocasionais: 02/08/2024 - 02/11/2024
        startDate = endDate;
        endDate = startDate.minusDays(90);
        List<Client> ocasionais = schedulingRepository.findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(endDate,startDate,"ROLE_EMPLOYEE");
        numbers.add((long)ocasionais.size());
        System.out.println("Ocasionais: " + ocasionais); // Log para depuração

        // Verifica se os números correspondem aos agendamentos esperados
        numbers.forEach(s -> System.out.println("Resultado de Agendamento: " + s));

        // Configura os valores de retorno do DTO
        frenquencyDTO.setFrequentes(frequentes.size());
        frenquencyDTO.setMedios(medios.size());
        frenquencyDTO.setOcasionais(ocasionais.size());

        return frenquencyDTO;
    }
}
