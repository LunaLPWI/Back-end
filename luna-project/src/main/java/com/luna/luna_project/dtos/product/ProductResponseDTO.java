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
public class ProductResponseDTO {
    @NotNull
    private Long id;
    @NotBlank
    String name;
    @Min(0)
    @NotNull
    private Integer amount;
    @Min(0)
    @NotNull
    private Double price;
}
