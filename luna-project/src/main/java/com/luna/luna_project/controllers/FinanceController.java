package com.luna.luna_project.controllers;

import com.luna.luna_project.csv.agendamento.SchedulingCSV;
import com.luna.luna_project.dtos.EmployeeServiceCount;
import com.luna.luna_project.dtos.FrenquencyDTO;
import com.luna.luna_project.services.FinanceService;
import com.luna.luna_project.services.SchedulingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/finance")


public class FinanceController {

    private final FinanceService financeService;
    private final SchedulingCSV schedulingCSV;


    public FinanceController(SchedulingService schedulingService, FinanceService financeService, SchedulingCSV schedulingCSV) {
        this.financeService = financeService;
        this.schedulingCSV = schedulingCSV;
    }



    @GetMapping ("/revenue/services")
    public List<Double> revenueServices(@RequestParam Long establishmentId){
        return financeService.formRevenueScheduleServicesValues(establishmentId);
    }


    //mesma coisa do anterior porem para quantidade de serviços feitos
    @GetMapping("/quantity/services")
    public List<Long> revenueServicesLineQTT(@RequestParam Long establishmentId){
        return financeService.formRevenueScheduleServicesQtt(establishmentId);
    }


    //Retorna a a quantidade de serviços por funcionário
    @GetMapping("/quantity/services-employee/{establishmentId}")
    public List<EmployeeServiceCount> qttQuantityServices(@PathVariable Long establishmentId) {
        return financeService.getServicesPerEmployeeLast30Days(establishmentId);
    }

    //Retorna a frequencia dos clientes
    @GetMapping("/revenue/frequence")
    public FrenquencyDTO frequence(Long stablishmentId){
        return financeService.formFrequencyScheduleServices(stablishmentId);
    }

    private Path diretorioBase = Paths.get("src/arquivos");

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(){
//        schedulingCSV.write();

        File file = this.diretorioBase.resolve("FinanceArchive.csv").toFile();
        try {
            InputStream fileInputStream = new FileInputStream(file);

            return ResponseEntity.status(200)
                    .header("Content-Disposition",
                            "attachment; filename=" + "FinanceArchive.csv")
                    .body(fileInputStream.readAllBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Diretório não encontrado", null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível converter para byte[]", null);
        }
    }

    private String formatarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID(), nomeOriginal);
    }
}
