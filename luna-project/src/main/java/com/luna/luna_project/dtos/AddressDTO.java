package com.luna.luna_project.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressDTO {
    private String cep;
    private String logradouro;
    @NotNull(message = "O número do endereço é obrigatório.")
    private Integer number;
    private String complemento;
    private String localidade;
    private String bairro;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}

