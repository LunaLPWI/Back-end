package com.luna.luna_project.controllers;



import com.luna.luna_project.dtos.agendamentos.AgendamentoRequestDTO;
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

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(long id){
        agendamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> saveAgendamento(@RequestBody @Valid
                                                                      AgendamentoRequestDTO agendamentoRequestDTO){
        Agendamento agendamento = agendamentoService.agendamentoSave
                (AgendamentoMapper.RequestToEntity(agendamentoRequestDTO));
        return ResponseEntity.ok(AgendamentoMapper.EntityToResponse(agendamento));
    }
    @GetMapping("/agendamento-vagos")
    public ResponseEntity<List<LocalDateTime>> getHorariosVagos(LocalDateTime inicio, LocalDateTime fim, Long idClient){
        return ResponseEntity.ok(agendamentoService.listHorariosDisponiveis(idClient,inicio,fim));
    }
    @GetMapping("/agendamento-ocupado")
    public ResponseEntity<Set<LocalDateTime>>getHorariosCheios(LocalDateTime inicio, LocalDateTime fim, Long idClient){
        return ResponseEntity.ok(agendamentoService.listHorariosOcupados(idClient,inicio,fim));
    }

//    @GetMapping("/agendamento-ocupado-admin")
//    public ResponseEntity<>

}
