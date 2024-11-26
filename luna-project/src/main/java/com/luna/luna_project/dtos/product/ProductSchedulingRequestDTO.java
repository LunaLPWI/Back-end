package com.luna.luna_project.dtos.product;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductSchedulingRequestDTO {
    @Min(1)
    @NotNull
    private long productStockId;
    @Min(1)
    @NotNull
    private int qtd;
}
