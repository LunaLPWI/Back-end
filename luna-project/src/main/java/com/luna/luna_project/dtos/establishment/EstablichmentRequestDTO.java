package com.luna.luna_project.dtos.establishment;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.PlanDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstablichmentRequestDTO {
    @NotBlank
    private String name;
    private AddressDTO addressDTO;
    private PlanDTO planDTO;
    @NotBlank
    private String cnpj;

}