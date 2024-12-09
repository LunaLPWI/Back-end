package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.dtos.TaskDTO;
import com.luna.luna_project.enums.StatusScheduling;
import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.ProductScheduling;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
public class SchedulingClientDTO {
    Long id;
    private LocalDateTime startDateTime;
    private List<TaskDTO> items;
    private String nameEmployee;

}
