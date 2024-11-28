package com.luna.luna_project.dtos.client;
import com.luna.luna_project.models.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientLoginDTO {
    private String email;
    private String password;
}
