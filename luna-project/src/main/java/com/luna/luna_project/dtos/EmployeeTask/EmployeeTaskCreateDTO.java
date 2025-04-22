package com.luna.luna_project.dtos.EmployeeTask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTaskCreateDTO {
    private Long clientId;
    private List<EmployeeTaskDTO> tasks;
}
