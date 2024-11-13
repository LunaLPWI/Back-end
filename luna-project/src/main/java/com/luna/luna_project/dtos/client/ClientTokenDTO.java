package com.luna.luna_project.dtos.client;
import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Address;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ClientTokenDTO {
    private Long id;
    private String name;
    private String email;
    private String token;
    private Address address;
    private LocalDate birthDay;
    private String phoneNumber;
    private Set<String> roles;

}
