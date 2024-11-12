package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.enums.Task;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoRequestUpdateDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long idClient;
    @NotNull
    private Long idFunc;
    @Future
    private LocalDateTime dataHoraInicio;
    @NotEmpty
    @ElementCollection(targetClass = Task.class)
    private List<Task> itens;

}
