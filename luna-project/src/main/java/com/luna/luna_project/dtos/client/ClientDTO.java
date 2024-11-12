package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public record ClientDTO(
        Long id,
        @NotBlank(message = "O campo name não estar em branco.")
        String name,
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,8}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, um caractere especial e um número, com tamanho entre 6 e 8 caracteres.")
        String password,
        @Pattern(
                regexp = "^[1-9]{2}9?[0-9]{8}$",
                message = "O número de telefone deve estar no formato DDD9XXXXXXXX ou DDDXXXXXXXX, onde o DDD é válido e o número possui 10 ou 11 dígitos no total."
        )
        String phoneNumber,
        @NotBlank(message = "O campo CPF não deve estar em branco.")
        @CPF
        String cpf,
        @NotNull(message = "O campo birthDay não deve estar vazio.")
        @PastOrPresent(message = "A data de nascimento não pode ser no futuro.")
        LocalDate birthDay,
        AddressDTO address,
        Set<String> roles
) {
}
