package com.luna.luna_project.controllers;

import com.luna.luna_project.csv.agendamento.AgendamentoCSV;
import com.luna.luna_project.dtos.agendamentos.AgendamentoRequestDTO;
import com.luna.luna_project.dtos.agendamentos.AgendamentoRequestUpdateDTO;
import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseAdminDTO;
import com.luna.luna_project.dtos.agendamentos.AgendamentoResponseDTO;
import com.luna.luna_project.mapper.AgendamentoMapper;
import com.luna.luna_project.models.Agendamento;
import com.luna.luna_project.services.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;
    private final AgendamentoMapper agendamentoMapper; // Injeção do mapper

    public AgendamentoController(AgendamentoService agendamentoService, AgendamentoMapper agendamentoMapper) {
        this.agendamentoService = agendamentoService;
        this.agendamentoMapper = agendamentoMapper; // Atribuição do mapper
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        agendamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> saveAgendamento(@RequestBody @Valid AgendamentoRequestDTO agendamentoRequestDTO) {

        Agendamento agendamento = agendamentoService.agendamentoSave(agendamentoMapper.RequestToEntity(agendamentoRequestDTO));
        return ResponseEntity.ok(agendamentoMapper.EntityToResponse(agendamento));
    }

    @GetMapping("/agendamento-vagos")
    public ResponseEntity<List<LocalDateTime>> getHorariosVagos(@RequestParam LocalDateTime inicio,
                                                                @RequestParam LocalDateTime fim,
                                                                @RequestParam Long idFunc,
                                                                @RequestParam Long idClient) {
        return ResponseEntity.ok(agendamentoService.listHorariosDisponiveis(idFunc,idClient, inicio, fim));
    }

    @GetMapping("/agendamento-client")
    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentos(@RequestParam LocalDateTime inicio,
                                                                @RequestParam Long idClient) {
        List<Agendamento> agendamentos = agendamentoService.listarAgendamentosByClientId(idClient, inicio);
        List<AgendamentoResponseDTO> agendamentoResponseDTOS = agendamentos.stream().map
                (agendamentoMapper::EntityToResponse).toList();
        return ResponseEntity.ok(agendamentoResponseDTOS);
    }

    @GetMapping("/agendamento-ocupado")
    public ResponseEntity<Set<LocalDateTime>> getHorariosCheios(@RequestParam LocalDateTime inicio,
                                                                @RequestParam LocalDateTime fim,
                                                                @RequestParam Long idClient) {
        return ResponseEntity.ok(agendamentoService.listHorariosOcupados(idClient, inicio, fim));
    }



    @GetMapping("/agendamento-ocupado-admin")
    public ResponseEntity<List<AgendamentoResponseAdminDTO>> getAgendamentosClientsAdmin(@RequestParam LocalDateTime inicio,
                                                                                         @RequestParam LocalDateTime fim,
                                                                                         @RequestParam Long idFunc) {
        List<Agendamento> agendamentos = agendamentoService.listarAgendamentosbyFuncId(idFunc, inicio, fim);
        AgendamentoCSV agendamentoCSV = new AgendamentoCSV(agendamentoService,agendamentoMapper);
        agendamentoCSV.write(idFunc,inicio,fim);
        return ResponseEntity.ok(agendamentos.stream()
                .map(agendamentoMapper::EntityToResponseAdmin).toList());
    }

    @PatchMapping
    public ResponseEntity<AgendamentoResponseDTO> updateById(@RequestBody @Valid AgendamentoRequestUpdateDTO agendamentoRequestUpdateDTO) {
        Agendamento agendamento = agendamentoMapper.RequestUpdateToEntity(agendamentoRequestUpdateDTO);
        agendamentoService.updateAgendamento(agendamento);

        return ResponseEntity.ok(agendamentoMapper.EntityToResponse(agendamento));
    }


}
