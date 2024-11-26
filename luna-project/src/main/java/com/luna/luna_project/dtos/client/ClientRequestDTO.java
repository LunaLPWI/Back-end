package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@Data
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
    private AddressDTO address;
    @Past
    private LocalDate birthDay;
    private Set<String> roles;
}


