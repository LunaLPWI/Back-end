package com.luna.luna_project.dtos.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class ProductRequestDTO {

    @NotBlank
    String nome;
    @NotNull
    @Min(0)
    private Integer amount;
    @NotBlank
    private String description;
    @Min(0)
    @NotNull
    private Double price;
}
