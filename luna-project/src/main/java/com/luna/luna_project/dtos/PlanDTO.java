package com.luna.luna_project.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
public class PlanDTO {

    private Long id;
    private String name;
    private Integer interval;
    private Integer repeats;
    private LocalDateTime created_at;
    private String plan_id;
}
