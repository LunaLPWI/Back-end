package com.luna.luna_project.dtos;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String localidade;
    private String bairro;
    private String uf;
    private int number;
}

