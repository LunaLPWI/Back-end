package com.luna.luna_project.controllers;

import com.luna.luna_project.csv.agendamento.SchedulingCSV;
import com.luna.luna_project.dtos.agendamentos.*;
import com.luna.luna_project.enums.StatusScheduling;
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

    //deleta com base no id
    @Secured("ROLE_EMPLOYEE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        schedulingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    //salva um novo agendamento, tem verificação caso sobreponha horários de agendamentos ja existentes, volta 409 caso isso aconteça
    @PostMapping
    public ResponseEntity<SchedulingResponseDTO> saveScheduling(@RequestBody @Valid SchedulingRequestDTO schedulingRequestDTO) {

        Scheduling scheduling = schedulingService.schedulingSave(schedulingMapper.RequestToEntity(schedulingRequestDTO));
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }
    //devolve os horários válidos para um novo agendamento, passando horário de inicio fim e id do cliente e funcionários
    @GetMapping("/vacant-schedules")
    public ResponseEntity<List<LocalDateTime>> getVacantSchedules(@RequestParam LocalDateTime start,
                                                                  @RequestParam LocalDateTime end,
                                                                  @RequestParam Long employeeId,
                                                                  @RequestParam Long clientId) {
        return ResponseEntity.ok(schedulingService.listAvailable(employeeId,clientId, start, end));
    }
    // retorna os agendamentos referente ao cliente com base no id
    @GetMapping("/client-schedules")
    public ResponseEntity<List<SchedulingResponseDTO>> getScheduling(@RequestParam LocalDateTime start,
                                                                     @RequestParam Long clientId) {
        List<Scheduling> schedulings = schedulingService.listSchedulingByClientId(clientId, start);
        List<SchedulingResponseDTO> schedulingResponseDTOS = schedulings.stream().map
                (schedulingMapper::EntityToResponse).toList();
        return ResponseEntity.ok(schedulingResponseDTOS);
    }

    @GetMapping("/busy-schedules")
    public ResponseEntity<Set<LocalDateTime>> getFullSchedules(@RequestParam LocalDateTime start,
                                                               @RequestParam LocalDateTime end,
                                                               @RequestParam Long clientId) {
        return ResponseEntity.ok(schedulingService.listBusySchedules(clientId, start, end));
    }

    @Secured("ROLE_EMPLOYEE")
    @GetMapping("/busy-schedules-admin")
    public ResponseEntity<List<SchedulingResponseAdminDTO>> getSchedulingClientsAdmin(@RequestParam LocalDateTime start,
                                                                                      @RequestParam LocalDateTime end,
                                                                                      @RequestParam Long employeeId) {
        List<Scheduling> schedulings = schedulingService.listSchedulingByEmployeeId(employeeId, start, end);

        return ResponseEntity.ok(schedulings.stream()
                .map(schedulingMapper::EntityToResponseAdmin).toList());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<SchedulingResponseDTO> updateById(@RequestBody @Valid SchedulingRequestUpdateDTO schedulingRequestUpdateDTO) {
        Scheduling scheduling = schedulingMapper.RequestUpdateToEntity(schedulingRequestUpdateDTO);
        schedulingService.updateScheduling(scheduling);
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/add-products")
    public ResponseEntity<SchedulingResponseDTO> addProductSchedule(@RequestBody @Valid SchedulingProductDTO schedulingProductDTO) {
        Scheduling scheduling = schedulingService.addProducts(schedulingProductDTO.getId(), schedulingProductDTO.getProducts());
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/remove-products")
    public ResponseEntity<SchedulingResponseDTO> addProductSchedule(@RequestParam Long schedulingId,@RequestParam  Long ProductSchduleId) {
        Scheduling scheduling = schedulingService.removeProduct(schedulingId,ProductSchduleId);
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }


    /// Para mudar os status deve passar o id do agendamento e o enum deseja sendo as opções:
    ///
    ///     PENDING, (pendente)
    ///     CONCLUDED, (Concluído)
    ///     DELAYED  (Atrasado)
    ///
    @Secured("ROLE_ADMIN")
    @PutMapping("/change-status")
    public ResponseEntity<SchedulingResponseDTO> changeStatus(@RequestParam Long schedulingId,@RequestParam StatusScheduling statusScheduling) {
        Scheduling scheduling = schedulingService.changeStatus(schedulingId,statusScheduling);
        return ResponseEntity.ok(schedulingMapper.EntityToResponse(scheduling));
    }



}
