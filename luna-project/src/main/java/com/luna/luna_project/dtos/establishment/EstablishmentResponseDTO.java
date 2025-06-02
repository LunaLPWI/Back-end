package com.luna.luna_project.dtos.establishment;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstablishmentResponseDTO {
    private Long id;
    private String name;
    private AddressDTO addressDTO;
//    private OneStepDTO oneStepDTO;
    private String cnpj;
    private Time openHour;
    private Time closeHour;
    private Boolean favorite;
    private double lat;
    private double lng;
    private double avarageRating;
}