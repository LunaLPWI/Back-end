package com.luna.luna_project.dtos;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ResetPasswordDTO {

    private String email;
    private String newPassword;
}
