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
    String name;
    @NotNull
    @Min(0)
    private Integer amount;
    @Min(0)
    @NotNull
    private Double price;
    private String mark;
    private String category;
}
