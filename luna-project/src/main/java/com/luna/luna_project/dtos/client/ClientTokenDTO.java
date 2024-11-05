package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Address;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientTokenDTO {
    private Long id;
    private String nome;
    private String email;
    private String token;
    private Address address;
    private Boolean isAdmin = false;
    private Boolean isFuncionario = false;
    private String cellphone;
    private List<String> roles;

}
