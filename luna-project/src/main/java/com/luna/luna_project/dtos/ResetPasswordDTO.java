package com.luna.luna_project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordDTO {

    private String email;
    private String newPassword;
}
