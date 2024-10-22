package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.enums.Task;
import jakarta.persistence.ElementCollection;
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
public class AgendamentoResponseAdminDTO {
    @NotBlank
    Long id;
    @Future
    private LocalDateTime dataHoraInicio;
    @Future
    private LocalDateTime dataHoraFim;
    @NotBlank
    private String nomeClient;
}
