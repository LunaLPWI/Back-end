package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskDTO;
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
    private Long id;
    private LocalDateTime startDateTime;
    private List<EmployeeTaskDTO> items;
    private String nameEmployee;
    private String stablishmentName;
    private String status;

}
