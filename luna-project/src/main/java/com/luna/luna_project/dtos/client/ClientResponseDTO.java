package com.luna.luna_project.dtos.client;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Client;
import lombok.*;
import org.antlr.v4.runtime.Token;

import java.util.List;


@Builder
@Getter
@Setter
@Data
public class ClientResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cellphone;
    private List<String> roles;
}



