package com.luna.luna_project.dtos.establishment;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstablishmentRequestDTO {
    @NotBlank
    private String name;
    private AddressDTO addressDTO;
    private OneStepDTO oneStepDTO;
    @NotBlank
    private String cnpj;
    private Time openHour;
    private Time closeHour;
    private Boolean favorite;

}