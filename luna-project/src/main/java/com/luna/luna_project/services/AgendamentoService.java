package com.luna.luna_project.services;

import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseAdminDTO;
import com.luna.luna_project.exceptions.ValidationException;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
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

    public Set<LocalDateTime> listHorariosOcupados(Long clientId,
                                                   LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        List<Agendamento> agendamentos = agendamentoRepository.
                findAgendamentoByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);
        Set<LocalDateTime> horariosOcupados = new HashSet<>();

        for (Agendamento agendamento : agendamentos) {
            LocalDateTime inicio = agendamento.getDataHoraInicio();
            LocalDateTime fim = agendamento.calcularDataFim();
            for (LocalDateTime horario = inicio; horario.isBefore(fim); horario = horario.plusMinutes(30)) {
                horariosOcupados.add(horario);
            }
        }

        if (horariosOcupados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Não há ocupados horários neste intervalo");
        }

        return horariosOcupados;
    }

    public List<LocalDateTime> listHorariosDisponiveis(Long idFunc, Long clientId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        // Verifica se o cliente e o funcionário existem


        // Obtém os agendamentos do cliente e do funcionário dentro do período especificado
        List<Agendamento> agendamentosFunc = agendamentoRepository
                .findAgendamentoByFuncionario_IdAndDataHoraInicioBetween(idFunc, dataHoraInicio, dataHoraFim);

        List<Agendamento> agendamentosClient = agendamentoRepository
                .findAgendamentoByClient_IdAndDataHoraInicioBetween(clientId, dataHoraInicio, dataHoraFim);

        Set<Agendamento> agendamentos = new HashSet<>();

        agendamentos.addAll(agendamentosFunc);
        agendamentos.addAll(agendamentosClient);
        agendamentos.stream().sorted(Comparator.comparing(Agendamento::getDataHoraInicio));

        // Gera todos os horários possíveis dentro do período

        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();
        for (LocalDateTime horario = dataHoraInicio; horario.plusMinutes(45).isBefore(dataHoraFim); horario = horario.plusMinutes(45)) {
            horariosDisponiveis.add(horario);
        }

        for (Agendamento agendamento : agendamentos) {
            horariosDisponiveis.removeIf(horario ->
                    (horario.isBefore(agendamento.calcularDataFim()) && horario.plusMinutes(45).isAfter(agendamento.getDataHoraInicio()))
            );
        }

        // Verifica se há horários disponíveis
        if (horariosDisponiveis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há horários disponíveis entre " + dataHoraInicio + " e " + dataHoraFim);
        }

        return horariosDisponiveis;
    }




    public List<Agendamento> listarAgendamentosbyFuncId(Long funcId, LocalDateTime dataHoraInicio,
                                                        LocalDateTime dataHoraFim) {


        return agendamentoRepository.findAgendamentoByFuncionario_IdAndDataHoraInicioBetween(funcId,
                dataHoraInicio, dataHoraFim);
    }

    public List<Agendamento> listarAgendamentosByClientId(Long clientId, LocalDateTime dateTimeInicio){

        List<Agendamento> agendamentos =  agendamentoRepository.findAgendamentoByClient_IdAndDataHoraInicioAfter
                (clientId,dateTimeInicio);
        if(agendamentos.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NO_CONTENT,"Não há agendamentos para este usuários a partir deste dia e horário");
        }
        return agendamentos;
    }

    public Agendamento agendamentoSave(Agendamento agendamento) {
        agendamento.setId(null);
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "CPF já cadastrado Agendamento de id: %d não encontrado".formatted(id));
        }
        agendamentoRepository.deleteById(id);
    }



    public Agendamento updateAgendamento(Agendamento agendamento){
        Optional <Agendamento> agendamentoOptional =  agendamentoRepository.findById(agendamento.getId());
        if(agendamentoOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe agendamento com o id:%d".formatted(agendamento.getId()));
        };
        agendamentoRepository.save(agendamento);
        return agendamentoOptional.get();
    }
}
