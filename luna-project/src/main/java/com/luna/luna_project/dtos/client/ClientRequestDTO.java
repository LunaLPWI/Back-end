package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@Data
public class ClientRequestDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String cellphone;
    private String password;
    private AddressDTO address;
    private Boolean isAdmin;
    private Boolean isFuncionario;
    private Set<String> roles;
}


