package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import lombok.*;


@Builder
@Getter
@Setter
@Data
public class ClientRequestDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String password;
    private AddressDTO address;
}


