package com.luna.luna_project.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {
    @NotBlank
    private String cep;
    @NotBlank
    private String complemento;
}
