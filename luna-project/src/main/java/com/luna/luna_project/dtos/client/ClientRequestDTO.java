package com.luna.luna_project.dtos.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
public class ClientRequestDTO {
    private Long id;
    @NotBlank
    private String name;
    @CPF
    @NotBlank
    private String cpf;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password;
    @Past
    private LocalDate birthDay;
    private Set<String> roles;

}


