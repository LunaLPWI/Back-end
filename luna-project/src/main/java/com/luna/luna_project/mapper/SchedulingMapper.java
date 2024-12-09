package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.agendamentos.*;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.TaskMapper;
import com.luna.luna_project.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulingMapper {

    private final ClientService clientService;
    private final TaskMapper taskMapper;

    @Autowired
    public SchedulingMapper(ClientService clientService, TaskMapper taskMapper) {
        this.clientService = clientService;
        this.taskMapper = taskMapper;
    }

    public Scheduling RequestToEntity(SchedulingRequestDTO schedulingRequestDTO) {
        return Scheduling.builder()
                .client(clientService.searchClientById(schedulingRequestDTO.getClientId()))
                .employee(clientService.searchClientById(schedulingRequestDTO.getEmployeeId()))
                .startDateTime(schedulingRequestDTO.getStartDateTime())
                .items(schedulingRequestDTO.getItems())
                .build();
    }
    public Scheduling RequestUpdateToEntity(SchedulingRequestUpdateDTO agendamentoRequestDTO) {
        return Scheduling.builder()
                .id(agendamentoRequestDTO.getId())
                .client(clientService.searchClientById(agendamentoRequestDTO.getClientId()))
                .employee(clientService.searchClientById(agendamentoRequestDTO.getEmployeeId()))
                .startDateTime(agendamentoRequestDTO.getStartDateTime())
                .items(agendamentoRequestDTO.getItems())
                .build();
    }

    public SchedulingResponseDTO EntityToResponse(Scheduling scheduling) {
        return SchedulingResponseDTO.builder()
                .id(scheduling.getId())
                .clientId(scheduling.getClient().getId())
                .employeeId(scheduling.getEmployee().getId())
                .startDateTime(scheduling.getStartDateTime())
                .items(scheduling.getItems().stream().map(taskMapper::taskToTaskDTO).toList())
                .products(scheduling.getProducts())
                .statusScheduling(scheduling.getStatusScheduling())
                .build();
    }

    public SchedulingResponseAdminDTO EntityToResponseAdmin(Scheduling scheduling) {
        return SchedulingResponseAdminDTO.builder()
                .id(scheduling.getId())
                .endDateTime(scheduling.calculateEndDate())
                .startDateTime(scheduling.getStartDateTime())
                .clientName(scheduling.getClient().getName())
                .products(scheduling.getProducts())
                .items(scheduling.getItems())
                .statusScheduling(scheduling.getStatusScheduling())
                .build();
    }
    public SchedulingClientDTO EntityToClientSchedulling(Scheduling scheduling) {
        return SchedulingClientDTO.builder()
                .id(scheduling.getId())
                .startDateTime(scheduling.getStartDateTime())
                .nameEmployee(scheduling.getEmployee().getName())
                .items(scheduling.getItems().stream().map(taskMapper::taskToTaskDTO).toList())
                .build();
    }
}
