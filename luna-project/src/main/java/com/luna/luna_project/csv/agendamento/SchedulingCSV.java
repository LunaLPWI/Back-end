package com.luna.luna_project.csv.agendamento;

import com.luna.luna_project.dtos.agendamentos.SchedulingResponseAdminDTO;
import com.luna.luna_project.mapper.SchedulingMapper;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.services.SchedulingService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SchedulingCSV {

    final SchedulingService schedulingService;
    final SchedulingMapper schedulingMapper;

    public SchedulingCSV(SchedulingService schedulingService, SchedulingMapper schedulingMapper) {
        this.schedulingService = schedulingService;
        this.schedulingMapper = schedulingMapper;
    }

    public void write(Long employeeId, LocalDateTime start, LocalDateTime end) {

        File pastaArquivos = new File("arquivos");

        // Cria o diretório caso não exista
        if (!pastaArquivos.exists()) {
            pastaArquivos.mkdir(); // Cria apenas a pasta 'arquivos'
        }

        File arquivoAgendamento = new File(pastaArquivos, "FinanceArchive.csv");

        try (OutputStream outputStream = new FileOutputStream(arquivoAgendamento);
             BufferedWriter escritor = new BufferedWriter(
                     new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            escritor.write("id;startDateTime;endDateTime;clientName\n");

            // Recupera os agendamentos e escreve no arquivo
            List<Scheduling> agendamentosEntity = schedulingService.listSchedulingByEmployeeId(employeeId, start, end);
            List<SchedulingResponseAdminDTO> agendamentos = agendamentosEntity.stream()
                    .map(schedulingMapper::EntityToResponseAdmin).toList();

            for (SchedulingResponseAdminDTO agendamento : agendamentos) {
                escritor.write(agendamento.toString());
                escritor.write("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
