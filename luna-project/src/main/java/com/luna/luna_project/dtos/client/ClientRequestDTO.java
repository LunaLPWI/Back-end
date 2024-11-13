package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.addresses.AddressDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Getter
@Setter
@Data
public class ClientRequestDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String password;
    private AddressDTO address;
    private LocalDate birthDay;
    private Set<String> roles;
}


