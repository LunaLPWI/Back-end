package com.luna.luna_project.csv.agendamento;

import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseAdminDTO;
import com.luna.luna_project.mapper.AgendamentoMapper;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Component
public class AgendamentoCSV {

    final AgendamentoService agendamentoService;
    final AgendamentoMapper agendamentoMapper;

    public AgendamentoCSV(AgendamentoService agendamentoService, AgendamentoMapper agendamentoMapper) {
        this.agendamentoService = agendamentoService;
        this.agendamentoMapper = agendamentoMapper;
    }

    public void write(Long idFunc, LocalDateTime inicio, LocalDateTime fim) {
        try (OutputStream outputStream = new FileOutputStream("agendamento.csv");
             BufferedWriter escritor = new BufferedWriter(
                     new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            // Cabeçalho
            escritor.write("id;dataHoraInicio;dataHoraFim;nomeClient\n");

            // Obter a lista de agendamentos
            List<Agendamento> agendamentosEntity = agendamentoService.listarAgendamentosbyFuncId(idFunc, inicio, fim);
            List<AgendamentoResponseAdminDTO> agendamentos = agendamentosEntity.stream()
                    .map(agendamentoMapper::EntityToResponseAdmin).toList();


            for (AgendamentoResponseAdminDTO agendamento : agendamentos) {
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
