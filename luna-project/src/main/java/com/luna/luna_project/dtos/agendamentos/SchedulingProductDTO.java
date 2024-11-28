package com.luna.luna_project.dtos.agendamentos;

import com.luna.luna_project.models.ProductScheduling;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SchedulingProductDTO {
    private Long id;
    private List<ProductScheduling> products;
}
