package com.luna.luna_project.dtos.plans;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PlanDTO {

    private Long id;
    @NotBlank(message = "Campo name não pode estar em branco.")
    private String name;
    @NotNull(message = "Campo interval não pode ser nulo.")
    private Integer interval;
    @NotNull(message = "Campo repeats não pode ser nulo.")
    private Integer repeats;
    private LocalDateTime created_at;
    private String plan_id;
}
