package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.enums.Task;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDTO {

    @NotBlank
    Long id;
    @Future
    private LocalDateTime dataHoraInicio;
    @NotBlank
    @ElementCollection(targetClass = Task.class)
    private List<Task> itens;
    @NotBlank
    private Long idClient;

}
