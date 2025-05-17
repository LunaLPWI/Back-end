package com.luna.luna_project.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDTO {

    private Long id;
    private String name;
    private Integer interval;
    private Integer repeats;
    private LocalDateTime created_at;
    private String plan_id;
}
