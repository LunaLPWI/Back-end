package com.luna.luna_project.dtos.client;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Client;
import lombok.*;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;
    private LocalDate birthDay;
    private Set<String> roles;
}



