package com.luna.luna_project.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    @NotNull
    private Double value;
    @NotBlank
    private  String description;
    @NotNull
    private  Integer duration;
}
