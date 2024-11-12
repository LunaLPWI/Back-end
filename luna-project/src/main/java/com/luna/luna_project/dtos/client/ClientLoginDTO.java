package com.luna.luna_project.dtos.client;
import com.luna.luna_project.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLoginDTO {
    private String email;
    private String password;
}
