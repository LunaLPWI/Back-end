package com.luna.luna_project.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
public record ClientDTO(
        Long id,
        @NotBlank(message = "O campo name não estar em branco.")
        String name,
        @NotBlank(message = "O campo CPF não deve estar em branco.")
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "O CPF não está no formato correto.")
        String cpf,
        AddressDTO address
) {
}
