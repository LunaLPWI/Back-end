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

    public List<LocalDateTime> listHorariosDisponiveis(Long idFunc,Long clientId, LocalDateTime dataHoraInicio,
                                                       LocalDateTime dataHoraFim) {
//        if (existsById(clientId)){
//            throw new ResponseStatusException
//                    (HttpStatus.NOT_FOUND,"Não há clientes com este id cadastrado");
//        }
//        if (existsById(idFunc)){
//            throw new ResponseStatusException
//                    (HttpStatus.NOT_FOUND,"Não há funcionários com este id cadastrado");
//        }

        List<Agendamento> agendamentos = agendamentoRepository.
                findAgendamentoByClient_IdAndClient_IdAndDataHoraInicioBetween(clientId,idFunc, dataHoraInicio, dataHoraFim);
        Set<LocalDateTime> horariosOcupados = new HashSet<>();
        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();


        for (int i = 0; i < agendamentos.size()-1; i++) {
            if (Duration.between(agendamentos.get(i).calcularDataFim(),
                    agendamentos.get(i + 1).getDataHoraInicio()).toMinutes() >= 45

            ){
                horariosDisponiveis.add(agendamentos.get(i + 1).getDataHoraInicio());
                calcularHorarios(horariosDisponiveis, agendamentos.get(i).calcularDataFim(),
                        agendamentos.get(i + 1).getDataHoraInicio());
            }

        }
        if(agendamentos.size()>1) {
            if (Duration.between(dataHoraInicio,
                    agendamentos.get(0).getDataHoraInicio()).toMinutes() >= 45) {

                horariosDisponiveis.add(dataHoraInicio);
                calcularHorarios(horariosDisponiveis, dataHoraInicio, agendamentos.get(0).getDataHoraInicio());
            }
            if (Duration.between(agendamentos.get(agendamentos.size() - 1).calcularDataFim(),
                    dataHoraFim).toMinutes() >= 45) {

                horariosDisponiveis.add(agendamentos.get(agendamentos.size() - 1).calcularDataFim());
                calcularHorarios(horariosDisponiveis, agendamentos.get(agendamentos.size() - 1).calcularDataFim(),
                        dataHoraFim);
            }
        }
        calcularHorarios(horariosDisponiveis, dataHoraInicio, dataHoraFim);

        if (horariosDisponiveis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Não há horários disponíveis");
        }

        return horariosDisponiveis;
    }

    public void calcularHorarios(List<LocalDateTime> list,LocalDateTime inicio, LocalDateTime fim) {
        if(!inicio.isEqual(fim)) {
            if (inicio.isBefore(fim)) {
                if (inicio.plusMinutes(45).isAfter(fim)) {
                    return;
                }
                list.add(inicio);
                inicio = inicio.plusMinutes(45);
                calcularHorarios(list, inicio, fim);
            }
            if (inicio.minusMinutes(45).isBefore(fim)) {
                return;
            }
            list.add(inicio);
            inicio =inicio.minusMinutes(45);
            calcularHorarios(list, inicio, fim);
        }
    }

    public List<Agendamento> listarAgendamentosbyFuncId(Long funcId, LocalDateTime dataHoraInicio,
                                                        LocalDateTime dataHoraFim) {
        if (existsById(funcId)){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Não há usuários com este id cadastrado");
        }

        return agendamentoRepository.findAgendamentoByFuncionario_IdAndDataHoraInicioBetween(funcId,
                dataHoraInicio, dataHoraFim);
    }

    public List<Agendamento> listarAgendamentosByClientId(Long clientId, LocalDateTime dateTimeInicio){

        if (existsById(clientId)){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Não há usuários com este id cadastrado");
        }
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
        if (existsById(agendamento.getClient().getId())){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Não há cliente com este id cadastrado");
        }
        if (existsById(agendamento.getFuncionario().getId())){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Não há Funcionário com este id cadastrado");
        }
        List<Agendamento> agendamentos = listarAgendamentosbyFuncId(agendamento.getFuncionario().getId(),
                agendamento.getDataHoraInicio(),agendamento.calcularDataFim());

        for(Agendamento agendamento1 : agendamentos){
            boolean inicioAnterior= agendamento.getDataHoraInicio().isBefore(agendamento1.getDataHoraInicio());
            boolean finalPosterior = agendamento.calcularDataFim().isAfter(agendamento1.calcularDataFim());

            boolean inicioMeio = inicioAnterior &&
                    agendamento.calcularDataFim().isBefore(agendamento1.calcularDataFim());
            boolean fimMeio = finalPosterior &&
                    agendamento.getDataHoraInicio().isBefore(agendamento1.calcularDataFim());
            boolean entre = agendamento.getDataHoraInicio().isAfter(agendamento1.getDataHoraInicio())
                    && agendamento.calcularDataFim().isBefore(agendamento1.calcularDataFim());
            boolean engloba = inicioAnterior && finalPosterior;

            if(inicioMeio||fimMeio||engloba||entre){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Já existe um agendamento neste intervalo de tempo");
            }
        }
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
