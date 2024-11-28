package com.luna.luna_project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
public class ResetPasswordDTO {

    private String email;
    private String newPassword;
}
