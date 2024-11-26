package com.luna.luna_project.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AddressDTO {
    private String cep;
    private String logradouro;
    @NotNull(message = "O número do endereço é obrigatório.")
    private Integer number;
    private String complemento;
    private String cidade;
    private String bairro;
    private String uf;
}

