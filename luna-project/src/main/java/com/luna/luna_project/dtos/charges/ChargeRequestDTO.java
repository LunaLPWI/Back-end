package com.luna.luna_project.dtos.charges;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChargeRequestDTO {
    @NotBlank
    private String name;
    @NotNull
    private Integer amount;
    @NotNull    
    private Integer value;
}

