package com.luna.luna_project.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {
    private String cep;
    private String complemento;
}
