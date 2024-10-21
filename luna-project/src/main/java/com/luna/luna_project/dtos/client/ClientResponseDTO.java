package com.luna.luna_project.dtos.client;

import com.luna.luna_project.dtos.AddressDTO;
import lombok.*;


@Builder
@Getter
@Setter
@Data
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cep;
}



