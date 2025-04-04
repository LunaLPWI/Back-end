package com.luna.luna_project.dtos.client;
import com.luna.luna_project.models.Address;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientLoginDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
