package com.luna.luna_project.services;

import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.repositories.AgendamentoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AgendamentoService {

    private AgendamentoRepository agendamentoRepository;



    public Boolean existsById(Long id) {
        return agendamentoRepository.existsById(id);
    }

    public Set<LocalDateTime> listHorariosOcupados(Long clientId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        List<Agendamento> agendamentos = agendamentoRepository.findByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);
        Set<LocalDateTime> horariosOcupados = new HashSet<>();
        for (Agendamento agendamento : agendamentos) {
            LocalDateTime inicio = agendamento.getDataHoraInicio();
            LocalDateTime fim = agendamento.calcularDataFim();

            for (LocalDateTime horario = inicio; horario.isBefore(fim); horario = horario.plusMinutes(30)) {
                horariosOcupados.add(horario);
            }
        }
        if(horariosOcupados.isEmpty()){
            return (Set<LocalDateTime>) new ValidationException("Não há horários ocupados");
        }
        return horariosOcupados;
    }

    public List<LocalDateTime> listHorariosDisponiveis(Long clientId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        List<Agendamento> agendamentos = agendamentoRepository.findByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);
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

        if(horariosDisponiveis.isEmpty()){
            return (List<LocalDateTime>) new ValidationException("Não há horários disponíveis");
        }
        return horariosDisponiveis;
    }


    public Agendamento agendamentoSave(Agendamento agendamento){
        agendamento.setId(null);
        return agendamentoRepository.save(agendamento);
    }


    public void deleteById (Long id){
        if(!agendamentoRepository.existsById(id)){
            throw new ValidationException("Agendamento de id: %d não encontrado".formatted(id));
        }
        agendamentoRepository.deleteById(id);
    }




}
