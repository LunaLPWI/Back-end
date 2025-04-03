package com.luna.luna_project.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public String formatAddress() {
        StringBuilder addressBuilder = new StringBuilder();

        if (logradouro != null && !logradouro.isEmpty()) {
            addressBuilder.append(logradouro);
        }

        if (number != null) {
            addressBuilder.append(", ").append(number);
        }

        if (bairro != null && !bairro.isEmpty()) {
            addressBuilder.append(" - ").append(bairro);
        }

        if (cidade != null && !cidade.isEmpty()) {
            addressBuilder.append(", ").append(cidade);
        }

        if (uf != null && !uf.isEmpty()) {
            addressBuilder.append(" - ").append(uf);
        }

        if (cep != null && !cep.isEmpty()) {
            addressBuilder.append(", CEP: ").append(cep);
        }

        return addressBuilder.toString().trim();
    }
}
