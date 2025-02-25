package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.dtos.TaskDTO;
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
