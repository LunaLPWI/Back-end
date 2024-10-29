package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.agendamentos.AgendamentoRequestDTO;
import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseAdminDTO;
import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseDTO;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.repositories.TaskMapper;
import com.luna.luna_project.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    private final ClientService clientService;
    private final TaskMapper taskMapper;

    @Autowired
    public AgendamentoMapper(ClientService clientService,TaskMapper taskMapper) {
        this.clientService = clientService;
        this.taskMapper = taskMapper;
    }

    public Agendamento RequestToEntity(AgendamentoRequestDTO agendamentoRequestDTO) {
        return Agendamento.builder()
                .client(clientService.searchClientById(agendamentoRequestDTO.getIdClient()))
                .funcionario(clientService.searchClientById(agendamentoRequestDTO.getIdFunc()))
                .dataHoraInicio(agendamentoRequestDTO.getDataHoraInicio())
                .itens(agendamentoRequestDTO.getItens())
                .build();
    }

    public AgendamentoResponseDTO EntityToResponse(Agendamento agendamento) {
        return AgendamentoResponseDTO.builder()
                .id(agendamento.getId())
                .idClient(agendamento.getClient().getId())
                .idFuncionario(agendamento.getFuncionario().getId())
                .dataHoraInicio(agendamento.getDataHoraInicio())
                .itens(agendamento.getItens().stream().map(taskMapper::taskToTaskDTO).toList())
                .build();
    }

    public AgendamentoResponseAdminDTO EntityToResponseAdmin(Agendamento agendamento) {
        return AgendamentoResponseAdminDTO.builder()
                .id(agendamento.getId())
                .dataHoraFim(agendamento.calcularDataFim())
                .dataHoraInicio(agendamento.getDataHoraInicio())
                .nomeClient(agendamento.getClient().getNome())
                .build();
    }
}
