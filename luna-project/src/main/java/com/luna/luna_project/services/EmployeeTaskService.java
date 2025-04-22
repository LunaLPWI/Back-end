package com.luna.luna_project.services;

import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskCreateDTO;
import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskDTO;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.EmployeeTask;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.EmployeeTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeTaskService {

    private final ClientRepository clientRepository;
    private final EmployeeTaskRepository taskRepository;

    public List<EmployeeTaskDTO> createTasksForEmployee(EmployeeTaskCreateDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        List<EmployeeTask> tasks = dto.getTasks().stream().map(taskDto ->
                EmployeeTask.builder()
                        .client(client)
                        .name(taskDto.getName())
                        .description(taskDto.getDescription())
                        .duration(taskDto.getDuration())
                        .value(taskDto.getValue())
                        .build()
        ).collect(Collectors.toList());

        return taskRepository.saveAll(tasks).stream()
                .map(task -> EmployeeTaskDTO.builder()
                        .name(task.getName())
                        .description(task.getDescription())
                        .value(task.getValue())
                        .duration(task.getDuration())
                        .build())
                .collect(Collectors.toList());
    }

    public List<EmployeeTaskDTO> getTasksByEmployee(Long clientId) {
        return taskRepository.findByClientId(clientId)
                .stream()
                .map(task -> EmployeeTaskDTO.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .value(task.getValue())
                        .duration(task.getDuration())
                        .build())
                .collect(Collectors.toList());
    }
}
