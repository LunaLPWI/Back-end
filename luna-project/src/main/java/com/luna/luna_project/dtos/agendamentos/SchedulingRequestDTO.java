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
public class SchedulingRequestDTO {

    @NotNull
    private Long clientId;
    @NotNull
    private Long employeeId;
    @Future
    private LocalDateTime startDateTime;
    @NotEmpty
    @ElementCollection(targetClass = Task.class)
    private List<Task> items;


}
