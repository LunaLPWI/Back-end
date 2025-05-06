package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskCreateDTO;
import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskDTO;
import com.luna.luna_project.models.EmployeeTask;
import com.luna.luna_project.services.EmployeeTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-tasks")
@RequiredArgsConstructor
public class EmployeeTaskController {

    private final EmployeeTaskService service;

    @PostMapping
    public ResponseEntity<List<EmployeeTaskDTO>> createTasks(@RequestBody EmployeeTaskCreateDTO dto) {
        List<EmployeeTaskDTO> tasks = service.createTasksForEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tasks);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<EmployeeTaskDTO>> getTasks(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.getTasksByEmployee(clientId));
    }

}
