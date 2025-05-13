package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.agendamentos.*;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.EmployeeTaskRepository;
import com.luna.luna_project.services.ClientService;
import com.luna.luna_project.services.EmployeeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SchedulingMapper {

    private final ClientService clientService;
    private final EmployeeTaskMapper employeeTaskMapper;

    @Autowired
    EmployeeTaskRepository employeeTaskRepository;


    @Autowired
    public SchedulingMapper(ClientService clientService, EmployeeTaskMapper employeeTaskMapper) {
        this.clientService = clientService;
        this.employeeTaskMapper = employeeTaskMapper;
    }

    public Scheduling RequestToEntity(SchedulingRequestDTO schedulingRequestDTO) {
        return Scheduling.builder()
                .client(clientService.searchClientById(schedulingRequestDTO.getClientId()))
                .employee(clientService.searchClientById(schedulingRequestDTO.getEmployeeId()))
                .items(schedulingRequestDTO.getItems().stream()
                        .map(id -> employeeTaskRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("EmployeeTask not found with id: " + id)))
                        .collect(Collectors.toList()))
                .startDateTime(schedulingRequestDTO.getStartDateTime())
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
                .items(scheduling.getItems().stream().map(employeeTaskMapper::toDTO).toList())
                .statusScheduling(scheduling.getStatusScheduling())
                .build();
    }

    public SchedulingResponseAdminDTO EntityToResponseAdmin(Scheduling scheduling) {
        return SchedulingResponseAdminDTO.builder()
                .id(scheduling.getId())
                .endDateTime(scheduling.calculateEndDate())
                .startDateTime(scheduling.getStartDateTime())
                .clientName(scheduling.getClient().getName())
                .items(scheduling.getItems())
                .statusScheduling(scheduling.getStatusScheduling())
                .build();
    }
    public SchedulingClientDTO EntityToClientSchedulling(Scheduling scheduling) {
        return SchedulingClientDTO.builder()
                .id(scheduling.getId())
                .startDateTime(scheduling.getStartDateTime())
                .nameEmployee(scheduling.getEmployee().getName())
                .stablishmentName(scheduling.getEmployee().getEstablishment().getName())
                .items(scheduling.getItems().stream().map(employeeTaskMapper::toDTO).toList())
                .status(scheduling.getStatusScheduling().toString())
                .build();
    }
}
