package com.luna.luna_project.dtos.establishment;

import com.luna.luna_project.dtos.OneStepDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstablishPlanRequestDTO {
    private String cnpj;
    private OneStepDTO oneStepDTO;
}
