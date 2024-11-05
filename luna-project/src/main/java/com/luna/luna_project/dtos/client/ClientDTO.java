package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ClientDTO(
        Long id,
        @NotBlank(message = "O campo name não estar em branco.")
        String nome,
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,8}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, um caractere especial e um número, com tamanho entre 6 e 8 caracteres.")
        String password,
        @NotBlank(message = "O campo CPF não deve estar em branco.")
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "O CPF não está no formato correto.")
        String cpf,
        AddressDTO address,
        Boolean isAdmin,
        Boolean isFuncionario,
        String cellphone,
        List<String> roles
) {
}
