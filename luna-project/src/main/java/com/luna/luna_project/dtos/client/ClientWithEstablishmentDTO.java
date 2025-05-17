package com.luna.luna_project.dtos.client;

import com.luna.luna_project.dtos.establishment.EstablichmentByClientDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ClientWithEstablishmentDTO {

    private Long id;
    @NotBlank
    private String name;
    private EstablichmentByClientDTO establishmentDTO;
}
