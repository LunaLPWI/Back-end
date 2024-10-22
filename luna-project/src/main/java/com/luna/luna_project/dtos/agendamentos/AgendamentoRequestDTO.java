package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.enums.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoRequestDTO {

    @NotBlank
    private Long idClient;
    private Long idFunc;
    @Future
    private LocalDateTime dataHoraInicio;
    @NotBlank
    @ElementCollection(targetClass = Task.class)
    private List<Task> itens;

}
