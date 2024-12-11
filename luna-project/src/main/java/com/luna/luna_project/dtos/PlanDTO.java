package com.luna.luna_project.dtos;

import lombok.Data;

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
