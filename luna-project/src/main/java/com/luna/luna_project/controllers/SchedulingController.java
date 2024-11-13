package com.luna.luna_project.controllers;

import com.luna.luna_project.csv.agendamento.SchedulingCSV;
import com.luna.luna_project.dtos.agendamentos.SchedulingRequestDTO;
import com.luna.luna_project.dtos.agendamentos.SchedulingRequestUpdateDTO;
import com.luna.luna_project.dtos.agendamentos.SchedulingResponseAdminDTO;
import com.luna.luna_project.dtos.agendamentos.SchedulingResponseDTO;
import com.luna.luna_project.mapper.SchedulingMapper;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.services.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/schedules")
public class SchedulingController {
    private final SchedulingService schedulingService;
    private final SchedulingMapper schedulingMapper; // Injeção do mapper

    public SchedulingController(SchedulingService schedulingService, SchedulingMapper schedulingMapper) {
        this.schedulingService = schedulingService;
        this.schedulingMapper = schedulingMapper; // Atribuição do mapper
    }
    @Secured("ROLE_FUNCIONARIO")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        schedulingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<SchedulingResponseDTO> saveScheduling(@RequestBody @Valid SchedulingRequestDTO schedulingRequestDTO) {

        Scheduling scheduling = schedulingService.schedulingSave(schedulingMapper.RequestToEntity(schedulingRequestDTO));
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }

    @GetMapping("/agendamento-vagos")
    public ResponseEntity<List<LocalDateTime>> getVacantSchedules(@RequestParam LocalDateTime start,
                                                                  @RequestParam LocalDateTime end,
                                                                  @RequestParam Long employeeId,
                                                                  @RequestParam Long clientId) {
        return ResponseEntity.ok(schedulingService.listAvailable(employeeId,clientId, start, end));
    }

    @GetMapping("/agendamento-client")
    public ResponseEntity<List<SchedulingResponseDTO>> getScheduling(@RequestParam LocalDateTime start,
                                                                     @RequestParam Long clientId) {
        List<Scheduling> schedulings = schedulingService.listSchedulingByClientId(clientId, start);
        List<SchedulingResponseDTO> schedulingResponseDTOS = schedulings.stream().map
                (schedulingMapper::EntityToResponse).toList();
        return ResponseEntity.ok(schedulingResponseDTOS);
    }


    @GetMapping("/agendamento-ocupado")
    public ResponseEntity<Set<LocalDateTime>> getFullSchedules(@RequestParam LocalDateTime start,
                                                               @RequestParam LocalDateTime end,
                                                               @RequestParam Long clientId) {
        return ResponseEntity.ok(schedulingService.listBusySchedules(clientId, start, end));
    }


    @Secured("ROLE_FUNCIONARIO")
    @GetMapping("/agendamento-ocupado-admin")
    public ResponseEntity<List<SchedulingResponseAdminDTO>> getSchedulingClientsAdmin(@RequestParam LocalDateTime start,
                                                                                      @RequestParam LocalDateTime end,
                                                                                      @RequestParam Long employeeId) {
        List<Scheduling> schedulings = schedulingService.listSchedulingByEmployeeId(employeeId, start, end);
        SchedulingCSV schedulingCSV = new SchedulingCSV(schedulingService, schedulingMapper);
        schedulingCSV.write(employeeId,start,end);
        return ResponseEntity.ok(schedulings.stream()
                .map(schedulingMapper::EntityToResponseAdmin).toList());
    }
    @Secured("ROLE_ADMIN")
    @PatchMapping
    public ResponseEntity<SchedulingResponseDTO> updateById(@RequestBody @Valid SchedulingRequestUpdateDTO schedulingRequestUpdateDTO) {
        Scheduling scheduling = schedulingMapper.RequestUpdateToEntity(schedulingRequestUpdateDTO);
        schedulingService.updateScheduling(scheduling);

        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }


}
