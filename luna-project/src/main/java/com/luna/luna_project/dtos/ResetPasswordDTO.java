package com.luna.luna_project.dtos;
import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String email;
    private String newPassword;
}
