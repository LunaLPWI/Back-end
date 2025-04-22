package com.luna.luna_project.dtos.EmployeeTask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTaskDTO {
    private long id;
    private String name;
    private String description;
    private Double value;
    private Integer duration;
}
