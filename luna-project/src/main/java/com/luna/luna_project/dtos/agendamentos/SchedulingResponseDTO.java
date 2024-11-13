package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.dtos.TaskDTO;
import com.luna.luna_project.enums.Task;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class SchedulingResponseDTO {

    @NotBlank
    Long id;
    @Future
    private LocalDateTime startDateTime;
    @NotEmpty
    @ElementCollection(targetClass = Task.class)
    private List<TaskDTO> items;
    @NotNull
    private Long clientId;
    @NotNull
    private Long employeeId;

}
