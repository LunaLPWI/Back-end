package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.enums.StatusScheduling;
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
public class SchedulingResponseAdminDTO {
    @NotBlank
    Long id;
    @Future
    private LocalDateTime startDateTime;
    @Future
    private LocalDateTime endDateTime;
    @NotBlank
    private String clientName;
    @ElementCollection(targetClass = Task.class)
    private List<Task> items;
    private StatusScheduling statusScheduling;
}
