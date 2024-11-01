package com.luna.luna_project.services;

import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseAdminDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClientService clientService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClientService clientService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clientService = clientService;
    }

    public Boolean existsById(Long id) {
        return agendamentoRepository.existsById(id);
    }

    public Set<LocalDateTime> listHorariosOcupados(Long clientId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        List<Agendamento> agendamentos = agendamentoRepository.findAgendamentoByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);
        Set<LocalDateTime> horariosOcupados = new HashSet<>();

        for (Agendamento agendamento : agendamentos) {
            LocalDateTime inicio = agendamento.getDataHoraInicio();
            LocalDateTime fim = agendamento.calcularDataFim();
            for (LocalDateTime horario = inicio; horario.isBefore(fim); horario = horario.plusMinutes(30)) {
                horariosOcupados.add(horario);
            }
        }

        if (horariosOcupados.isEmpty()) {
            throw new ValidationException("Não há horários ocupados");
        }

        return horariosOcupados;
    }

    public List<LocalDateTime> listHorariosDisponiveis(Long clientId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        List<Agendamento> agendamentos = agendamentoRepository.findAgendamentoByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);
        Set<LocalDateTime> horariosOcupados = new HashSet<>();

        for (Agendamento agendamento : agendamentos) {
            LocalDateTime inicio = agendamento.getDataHoraInicio();
            LocalDateTime fim = agendamento.calcularDataFim();

            for (LocalDateTime horario = inicio; horario.isBefore(fim); horario = horario.plusMinutes(30)) {
                horariosOcupados.add(horario);
            }
        }

        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();
        for (LocalDateTime horario = dataHoraInicio; horario.isBefore(dataHoraFim); horario = horario.plusMinutes(30)) {
            if (!horariosOcupados.contains(horario)) {
                horariosDisponiveis.add(horario);
            }
        }

        if (horariosDisponiveis.isEmpty()) {
            throw new ValidationException("Não há horários disponíveis");
        }

        return horariosDisponiveis;
    }

    public List<Agendamento> listarAgendamentosbyFuncId(Long funcId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        clientService.searchClientById(funcId);

        return agendamentoRepository.findAgendamentoByFuncionario_IdAndDataHoraInicioBetween(funcId, dataHoraInicio, dataHoraFim);
    }

    public Agendamento agendamentoSave(Agendamento agendamento) {
        agendamento.setId(null);
        List<Agendamento> agendamentos = listarAgendamentosbyFuncId(agendamento.getFuncionario().getId(),
                agendamento.getDataHoraInicio(),agendamento.calcularDataFim());

        for(Agendamento agendamento1 : agendamentos){
            boolean inicioAnterior= agendamento.getDataHoraInicio().isBefore(agendamento1.getDataHoraInicio());
            boolean finalPosterior = agendamento.calcularDataFim().isAfter(agendamento1.calcularDataFim());

            boolean inicioMeio = inicioAnterior && agendamento.calcularDataFim().isBefore(agendamento1.calcularDataFim());
            boolean fimMeio = finalPosterior && agendamento.getDataHoraInicio().isBefore(agendamento1.calcularDataFim());
            boolean entre = agendamento.getDataHoraInicio().isAfter(agendamento1.getDataHoraInicio())
                    && agendamento.calcularDataFim().isBefore(agendamento1.calcularDataFim());
            boolean engloba = inicioAnterior && finalPosterior;

            if(inicioMeio||fimMeio||engloba||entre){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um agendamento neste intervalo de tempo");
            }
        }
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ValidationException("Agendamento de id: %d não encontrado".formatted(id));
        }
        agendamentoRepository.deleteById(id);
    }
}
